package com.blockbank.blockbank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "stake_positions")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StakePosition {
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private BigDecimal amountStaked;
    private BigDecimal rewardEarned;

    private BigDecimal apy;

    private String stakeTxHash;
    private String unstakeTxHash;

    private boolean isActive;

    private LocalDateTime stakedAt;
    private LocalDateTime unstakedAt;

    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID();
        this.stakedAt = LocalDateTime.now();
        this.isActive = true;
    }
}
