// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

import "@openzeppelin/contracts/token/ERC20/IERC20.sol";
import "@openzeppelin/contracts/security/ReentrancyGuard.sol";
import "@openzeppelin/contracts/security/Pausable.sol";
import "@openzeppelin/contracts/access/Ownable.sol";
import {AggregatorV3Interface} from "@chainlink/contracts/src/v0.8/shared/interfaces/AggregatorV3Interface.sol";

contract BlockbankSwap is ReentrancyGuard, Pausable, Ownable {
    IERC20 public immutable bbusd;
    IERC20 public immutable bbeth;
    AggregatorV3Interface public immutable ethUsdPriceFeed;

    event SwappedBbusdToBbeth(address indexed user, uint256 bbusdIn, uint256 bbethOut);
    event SwappedBbethToBbusd(address indexed user, uint256 bbethIn, uint256 bbusdOut);
    event EmergencyWithdraw(address indexed to, uint256 bbethAmount, uint256 bbusdAmount);

    constructor(
        address _bbusd,
        address _bbeth,
        address _ethUsdPriceFeed
    ) Ownable(msg.sender){
        require(_bbusd != address(0), "Invalid BBUSD address");
        require(_bbeth != address(0), "Invalid BBETH address");
        require(_ethUsdPriceFeed != address(0), "Invalid price feed address");

        bbusd = IERC20(_bbusd);
        bbeth = IERC20(_bbeth);
        ethUsdPriceFeed = AggregatorV3Interface(_ethUsdPriceFeed);
    }

    function getEthUsdPrice() public view returns (uint256 price, uint8 decimals) {
        (
            , int256 rawPrice,, uint256 updatedAt,
        ) = ethUsdPriceFeed.latestRoundData();
        require(rawPrice > 0, "Invalid price");
        require(updatedAt >= block.timestamp - 1 hours, "Stale price");
        price = uint256(rawPrice);
        decimals = ethUsdPriceFeed.decimals();
    }

    function swapBbusdToBbeth(uint256 bbusdAmount, uint256 minBbethOut)
    external whenNotPaused nonReentrant {
    require(bbusdAmount > 0, "Zero input");
    (uint256 ethUsd, uint8 feedDecimals) = getEthUsdPrice();

    uint256 bbethAmount = (bbusdAmount * 10**(18 + feedDecimals - 6)) / ethUsd;

    require(bbethAmount >= minBbethOut, "Slippage: too little output");
    require(bbeth.balanceOf(address(this)) >= bbethAmount, "Insufficient BBETH in contract");

    require(bbusd.transferFrom(msg.sender, address(this), bbusdAmount), "BBUSD transfer fail");
    require(bbeth.transfer(msg.sender, bbethAmount), "BBETH transfer fail");

    emit SwappedBbusdToBbeth(msg.sender, bbusdAmount, bbethAmount);
    }

    function swapBbethToBbusd(uint256 bbethAmount, uint256 minBbusdOut)
    external whenNotPaused nonReentrant {
    require(bbethAmount > 0, "Zero input");
    (uint256 ethUsd, uint8 feedDecimals) = getEthUsdPrice();

    uint256 bbusdAmount = (bbethAmount * ethUsd) / (10**(feedDecimals + 18 - 6));

    require(bbusdAmount >= minBbusdOut, "Slippage: too little output");
    require(bbusd.balanceOf(address(this)) >= bbusdAmount, "Insufficient BBUSD in contract");

    require(bbeth.transferFrom(msg.sender, address(this), bbethAmount), "BBETH transfer fail");
    require(bbusd.transfer(msg.sender, bbusdAmount), "BBUSD transfer fail");

    emit SwappedBbethToBbusd(msg.sender, bbethAmount, bbusdAmount);
    }

    
    
    function depositBbusd(uint256 amount) external onlyOwner {
        require(amount > 0, "Zero deposit");
        require(bbusd.transferFrom(msg.sender, address(this), amount), "Deposit BBUSD fail");
    }

    function depositBbeth(uint256 amount) external onlyOwner {
        require(amount > 0, "Zero deposit");
        require(bbeth.transferFrom(msg.sender, address(this), amount), "Deposit BBETH fail");
    }

    function emergencyWithdraw(address to) external onlyOwner {
        require(to != address(0), "Zero address");
        uint256 bbethBal = bbeth.balanceOf(address(this));
        uint256 bbusdBal = bbusd.balanceOf(address(this));
        require(bbeth.transfer(to, bbethBal), "Withdraw BBETH fail");
        require(bbusd.transfer(to, bbusdBal), "Withdraw BBUSD fail");
        emit EmergencyWithdraw(to, bbethBal, bbusdBal);
    }

    function pause() external onlyOwner { _pause(); }
    function unpause() external onlyOwner { _unpause(); }

   function getEstimatedBbethForBbusd(uint256 bbusdAmount) external view returns (uint256) {
    (uint256 ethUsd, uint8 feedDecimals) = getEthUsdPrice();
    return (bbusdAmount * 10**(18 + feedDecimals - 6)) / ethUsd;
    }

    function getEstimatedBbusdForBbeth(uint256 bbethAmount) external view returns (uint256) {
    (uint256 ethUsd, uint8 feedDecimals) = getEthUsdPrice();
    return (bbethAmount * ethUsd) / (10**(feedDecimals + 18 - 6));
    }

}
