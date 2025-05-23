package com.blockbank.blockbank.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BalanceResponse {
    private String tokenName;
    private String tokenSymbol;
    private String tokenAddress;
    private BigDecimal balance;
}
