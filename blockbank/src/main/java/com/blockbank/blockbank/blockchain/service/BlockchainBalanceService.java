package com.blockbank.blockbank.blockchain.service;

import com.blockbank.blockbank.blockchain.contract.BlockbankETH;
import com.blockbank.blockbank.blockchain.contract.BlockbankUSD;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;

@Service
public class BlockchainBalanceService {
    private final Web3j web3j;
    private final Credentials credentials;
    private final ContractGasProvider gasProvider;

    public BlockchainBalanceService(Web3j web3j, Credentials credentials, ContractGasProvider gasProvider) {
        this.web3j = web3j;
        this.credentials = credentials;
        this.gasProvider = gasProvider;
    }

    @Value("${blockbank.bbeth.address}")
    private String bbethContractAddress;

    @Value("${blockbank.bbusd.address}")
    private String bbusdContractAddress;

    private TransactionManager transactionManager;

    @PostConstruct
    private void init() {
        this.transactionManager = new RawTransactionManager(web3j, credentials);
    }

    private BlockbankETH loadBBETH() {
        return BlockbankETH.load(bbethContractAddress, web3j, credentials, gasProvider);
    }

    private BlockbankUSD loadBBUSD() {
        return BlockbankUSD.load(bbusdContractAddress, web3j, credentials, gasProvider);
    }

    public BigInteger getBBETHBalance(String walletAddress) throws Exception {
        return loadBBETH().balanceOf(walletAddress).send();
    }

    public BigInteger getBBUSDBalance(String walletAddress) throws Exception {
        return loadBBUSD().balanceOf(walletAddress).send();
    }
}
