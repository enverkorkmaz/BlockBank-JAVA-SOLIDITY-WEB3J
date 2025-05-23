package com.blockbank.blockbank.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SwapRequest {
    private String fromToken;
    private String toToken;
    private BigDecimal amount;
}
