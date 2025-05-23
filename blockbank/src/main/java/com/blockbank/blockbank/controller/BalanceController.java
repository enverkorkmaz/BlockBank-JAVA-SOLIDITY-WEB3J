package com.blockbank.blockbank.controller;

import com.blockbank.blockbank.dto.response.BalanceResponse;
import com.blockbank.blockbank.security.BlockbankUserDetails;
import com.blockbank.blockbank.service.BalanceService;
import com.blockbank.blockbank.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
@RequestMapping("/api/balance")
public class BalanceController {

    private final BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @GetMapping("/balance")
    public BigInteger getEthBalance(@AuthenticationPrincipal BlockbankUserDetails userDetails) {
        return balanceService.getEthBalance(userDetails.getEthAddress());
    }

    @GetMapping("/bbeth")
    public BalanceResponse getBbethBalance(@AuthenticationPrincipal BlockbankUserDetails userDetails) {
        return balanceService.getBbethBalance(userDetails.getEthAddress());
    }

    @GetMapping("/bbusd")
    public BalanceResponse getBbusdBalance(@AuthenticationPrincipal BlockbankUserDetails userDetails) {
        return balanceService.getBbusdBalance(userDetails.getEthAddress());
    }
}
