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
@Table(name = "token_balances")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenBalance {
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String token;
    private BigDecimal balance;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID();
        this.updatedAt = LocalDateTime.now();
    }
}
