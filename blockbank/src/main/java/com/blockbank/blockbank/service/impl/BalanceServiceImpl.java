package com.blockbank.blockbank.service.impl;

import com.blockbank.blockbank.blockchain.service.BlockchainBalanceService;
import com.blockbank.blockbank.dto.response.BalanceResponse;
import com.blockbank.blockbank.service.BalanceService;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class BalanceServiceImpl implements BalanceService {

    private final Web3j web3j;
    private final BlockchainBalanceService blockchainBalanceService;

    public BalanceServiceImpl(Web3j web3j, BlockchainBalanceService blockchainBalanceService) {
        this.web3j = web3j;
        this.blockchainBalanceService = blockchainBalanceService;
    }


    @Override
    public BigInteger getEthBalance(String walletAddress) {
        try {
            EthGetBalance balance = web3j.ethGetBalance(walletAddress, DefaultBlockParameterName.LATEST).send();
            return balance.getBalance();
        } catch (Exception e) {
            throw new RuntimeException("ETH bakiyesi alınamadı", e);
        }
    }

    @Override
    public BalanceResponse getBbethBalance(String walletAddress) {
        try {
            BigInteger bbethRaw = blockchainBalanceService.getBBETHBalance(walletAddress);

            BigDecimal bbeth = new BigDecimal(bbethRaw).divide(BigDecimal.TEN.pow(18));

            BalanceResponse balanceResponse = BalanceResponse.builder()
                    .tokenAddress(walletAddress)
                    .tokenName("Blockbank ETH")
                    .tokenSymbol("BBETH")
                    .balance(bbeth).build();
            return balanceResponse;
        } catch (Exception e) {
            throw new RuntimeException("Blockchain balance fetch failed: " + e.getMessage(), e);
        }
    }

    @Override
    public BalanceResponse getBbusdBalance(String walletAddress) {
        try {
            BigInteger bbusdRaw = blockchainBalanceService.getBBUSDBalance(walletAddress);

            BigDecimal bbusd = new BigDecimal(bbusdRaw).divide(BigDecimal.TEN.pow(18));

            BalanceResponse balanceResponse = BalanceResponse.builder()
                    .tokenAddress(walletAddress)
                    .tokenName("Blockbank USD")
                    .tokenSymbol("BBUSD")
                    .balance(bbusd).build();
            return balanceResponse;
        } catch (Exception e) {
            throw new RuntimeException("Blockchain balance fetch failed: " + e.getMessage(), e);
        }
    }


}
