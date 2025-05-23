package com.blockbank.blockbank.entity;

import com.blockbank.blockbank.entity.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "swap_transactions")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SwapTransaction {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String fromToken;
    private String toToken;

    private BigDecimal amountIn;
    private BigDecimal amountOut;

    private String txHash;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }
}
