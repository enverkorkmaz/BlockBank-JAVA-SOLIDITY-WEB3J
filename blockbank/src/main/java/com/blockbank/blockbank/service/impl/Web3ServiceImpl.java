package com.blockbank.blockbank.service.impl;

import com.blockbank.blockbank.blockchain.contract.SwapMock;
import com.blockbank.blockbank.service.Web3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.RawTransactionManager;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
@Slf4j
public class Web3ServiceImpl implements Web3Service {

    private final Web3j web3j;

    
    @Value("${web3.private-key}")
    private String privateKey;

    
    private static final String CONTRACT_ADDRESS = "0x5FbDB2315678afecb367f032d93F642f64180aa3";


    public Web3ServiceImpl(Web3j web3j) {
        this.web3j = web3j;
    }

    @Override
    public String performSwap(String ethAddress, BigDecimal amount, String fromToken, String toToken) {
        try {
            Credentials credentials = Credentials.create(privateKey);
            RawTransactionManager txManager = new RawTransactionManager(web3j, credentials);
            DefaultGasProvider gasProvider = new DefaultGasProvider();

            // 1 ether örneği (gerçek hesaplama yapılabilir)
            BigInteger weiValue = amount.multiply(BigDecimal.TEN.pow(18)).toBigInteger();

            SwapMock contract = SwapMock.load(CONTRACT_ADDRESS, web3j, txManager, gasProvider);
            TransactionReceipt receipt = contract.swap(fromToken, toToken, weiValue).send();

            log.info("Swap transaction completed: {}", receipt.getTransactionHash());

            return receipt.getTransactionHash();

        } catch (Exception e) {
            log.error("Swap failed: {}", e.getMessage(), e);
            throw new RuntimeException("Swap işleminde hata oluştu");
        }
    }
}
