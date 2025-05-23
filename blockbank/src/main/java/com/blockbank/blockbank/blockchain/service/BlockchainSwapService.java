package com.blockbank.blockbank.blockchain.service;

import com.blockbank.blockbank.blockchain.contract.BlockbankSwap;
import com.blockbank.blockbank.blockchain.contract.BlockbankETH;
import com.blockbank.blockbank.blockchain.contract.BlockbankUSD;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.crypto.Credentials;

import java.math.BigInteger;

@Service
public class BlockchainSwapService {

    private final Web3j web3j;
    private final Credentials credentials;
    private final ContractGasProvider gasProvider;

    @Value("${blockbank.swap.address}")
    private String swapContractAddress;

    @Value("${blockbank.bbeth.address}")
    private String bbethContractAddress;

    @Value("${blockbank.bbusd.address}")
    private String bbusdContractAddress;

    private TransactionManager transactionManager;

    public BlockchainSwapService(Web3j web3j, Credentials credentials, ContractGasProvider gasProvider) {
        this.web3j = web3j;
        this.credentials = credentials;
        this.gasProvider = gasProvider;
    }

    @PostConstruct
    private void init() {
        this.transactionManager = new RawTransactionManager(web3j, credentials);
    }

    private BlockbankSwap loadSwapContract() {
        return BlockbankSwap.load(swapContractAddress, web3j, transactionManager, gasProvider);
    }

    private BlockbankETH loadBBETH() {
        return BlockbankETH.load(bbethContractAddress, web3j, credentials, gasProvider);
    }

    private BlockbankUSD loadBBUSD() {
        return BlockbankUSD.load(bbusdContractAddress, web3j, credentials, gasProvider);
    }

    public BigInteger getEstimatedBbethForBbusd(BigInteger bbusdAmount) throws Exception {
        return loadSwapContract().getEstimatedBbethForBbusd(bbusdAmount).send();
    }

    public BigInteger getEstimatedBbusdForBbeth(BigInteger bbethAmount) throws Exception {
        return loadSwapContract().getEstimatedBbusdForBbeth(bbethAmount).send();
    }

    public String swapBbusdToBbeth(BigInteger bbusdAmount, BigInteger minBbethOut) throws Exception {
        loadBBUSD().approve(swapContractAddress, bbusdAmount).sendAsync().get();
        TransactionReceipt receipt = loadSwapContract().swapBbusdToBbeth(bbusdAmount, minBbethOut).sendAsync().get();
        return receipt.getTransactionHash();
    }

    public String swapBbethToBbusd(BigInteger bbethAmount, BigInteger minBbusdOut) throws Exception {
        loadBBETH().approve(swapContractAddress, bbethAmount).sendAsync().get();
        TransactionReceipt receipt = loadSwapContract().swapBbethToBbusd(bbethAmount, minBbusdOut).sendAsync().get();
        return receipt.getTransactionHash();
    }
}