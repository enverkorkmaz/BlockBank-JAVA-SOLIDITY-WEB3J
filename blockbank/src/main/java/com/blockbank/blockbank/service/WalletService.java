package com.blockbank.blockbank.service;

import com.blockbank.blockbank.entity.User;

public interface WalletService {
    void createWalletForUser(User user);
    void createWalletForMe(User user, String address, String rawPrivateKey);
}
