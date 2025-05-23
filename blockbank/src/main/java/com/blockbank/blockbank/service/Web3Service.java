package com.blockbank.blockbank.service;

import java.math.BigDecimal;

public interface Web3Service {
   String performSwap(String fromAddress, BigDecimal amount, String fromToken, String toToken);
}
