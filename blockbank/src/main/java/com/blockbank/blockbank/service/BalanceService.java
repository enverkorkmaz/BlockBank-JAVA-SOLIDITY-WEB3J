package com.blockbank.blockbank.service;

import com.blockbank.blockbank.dto.response.BalanceResponse;

import java.math.BigInteger;

public interface BalanceService {
    BigInteger getEthBalance(String walletAddress);
    BalanceResponse getBbethBalance(String walletAddress);
    BalanceResponse getBbusdBalance(String walletAddress);
}
