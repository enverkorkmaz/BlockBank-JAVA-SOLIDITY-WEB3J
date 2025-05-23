package com.blockbank.blockbank.service.impl;

import com.blockbank.blockbank.entity.User;
import com.blockbank.blockbank.entity.Wallet;
import com.blockbank.blockbank.repository.WalletRepository;
import com.blockbank.blockbank.service.WalletService;
import com.blockbank.blockbank.util.AesUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    private final AesUtil aesUtil;



    public WalletServiceImpl(WalletRepository walletRepository, AesUtil aesUtil) {
        this.walletRepository = walletRepository;
        this.aesUtil = aesUtil;
    }

    @Override
    public void createWalletForUser(User user) {
        try{
            ECKeyPair keyPair = Keys.createEcKeyPair();
            String privateKey = keyPair.getPrivateKey().toString(16);
            String address = "0x" + Keys.getAddress(keyPair);
            String encryptedPrivateKey = aesUtil.encrypt(privateKey);

            Wallet wallet = Wallet.builder()
                    .user(user)
                    .address(address)
                    .privateKey(encryptedPrivateKey)
                    .build();

            walletRepository.save(wallet);

        }catch (Exception e){
            throw new RuntimeException("Error creating wallet for user: " + user.getUsername(), e);
        }
    }

    @Override
    public void createWalletForMe(User user, String address, String rawPrivateKey) {
        try {
            String encryptedPrivateKey = aesUtil.encrypt(rawPrivateKey);

            Wallet wallet = Wallet.builder()
                    .user(user)
                    .address(address)
                    .privateKey(encryptedPrivateKey)
                    .build();

            walletRepository.save(wallet);

        } catch (Exception e) {
            throw new RuntimeException("Wallet manuel kaydedilemedi: " + e.getMessage(), e);
        }
    }
}
