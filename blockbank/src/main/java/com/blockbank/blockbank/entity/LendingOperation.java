package com.blockbank.blockbank.entity;

import com.blockbank.blockbank.entity.enums.LendingType;
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
@Table(name = "lending_operations")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LendingOperation {
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Enumerated(EnumType.STRING)
    private LendingType type; // DEPOSIT, WITHDRAW, BORROW, REPAY

    private String token; // ETH / USDC
    private BigDecimal amount;

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
