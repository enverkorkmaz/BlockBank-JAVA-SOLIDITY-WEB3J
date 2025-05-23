package com.blockbank.blockbank.service.impl;

import com.blockbank.blockbank.blockchain.service.BlockchainSwapService;
import com.blockbank.blockbank.dto.request.SwapRequest;
import com.blockbank.blockbank.dto.response.SwapResponse;
import com.blockbank.blockbank.entity.SwapTransaction;
import com.blockbank.blockbank.entity.User;
import com.blockbank.blockbank.entity.enums.TransactionStatus;
import com.blockbank.blockbank.repository.SwapTransactionRepository;
import com.blockbank.blockbank.repository.UserRepository;
import com.blockbank.blockbank.service.SwapService;
import com.blockbank.blockbank.service.Web3Service;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SwapServiceImpl implements SwapService {
    private final SwapTransactionRepository swapTransactionRepository;
    private final UserRepository userRepository;
    private final Web3Service web3Service;
    private final BlockchainSwapService blockchainSwapService;

    public SwapServiceImpl(SwapTransactionRepository swapTransactionRepository, UserRepository userRepository, Web3Service web3Service, BlockchainSwapService blockchainSwapService) {
        this.swapTransactionRepository = swapTransactionRepository;
        this.userRepository = userRepository;
        this.web3Service = web3Service;
        this.blockchainSwapService = blockchainSwapService;
    }


    @Override
    @Transactional
    public SwapResponse swap(SwapRequest request, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String txHash;
        BigDecimal amountOut; // dummy for now

        try {
            BigInteger amountIn = request.getAmount().toBigInteger();
            BigInteger estimated;

            if (request.getFromToken().equalsIgnoreCase("BBUSD") && request.getToToken().equalsIgnoreCase("BBETH")) {
                estimated = blockchainSwapService.getEstimatedBbethForBbusd(amountIn);
                txHash = blockchainSwapService.swapBbusdToBbeth(amountIn, estimated);
            } else if (request.getFromToken().equalsIgnoreCase("BBETH") && request.getToToken().equalsIgnoreCase("BBUSD")) {
                estimated = blockchainSwapService.getEstimatedBbusdForBbeth(amountIn);
                txHash = blockchainSwapService.swapBbethToBbusd(amountIn, estimated);
            } else {
                throw new RuntimeException("Unsupported token pair");
            }

            amountOut = new BigDecimal(estimated);
        } catch (Exception e) {
            throw new RuntimeException("Swap failed: " + e.getMessage(), e);
        }

        SwapTransaction tx = SwapTransaction.builder()
                .id(UUID.randomUUID())
                .user(user)
                .fromToken(request.getFromToken())
                .toToken(request.getToToken())
                .amountIn(request.getAmount())
                .amountOut(amountOut)
                .txHash(txHash)
                .status(TransactionStatus.SUCCESS)
                .createdAt(LocalDateTime.now())
                .build();

        swapTransactionRepository.save(tx);

        return SwapResponse.builder()
                .txHash(txHash)
                .status(TransactionStatus.SUCCESS)
                .amountOut(amountOut)
                .build();
    }
}
