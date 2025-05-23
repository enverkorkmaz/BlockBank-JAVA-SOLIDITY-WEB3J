package com.blockbank.blockbank.dto.response;

import com.blockbank.blockbank.entity.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SwapResponse {
    private String txHash;
    private TransactionStatus status;
    private BigDecimal amountOut;
}
