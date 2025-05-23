package com.blockbank.blockbank.repository;

import com.blockbank.blockbank.entity.TokenBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface TokenBalanceRepository extends JpaRepository<TokenBalance, UUID> {
    Optional<TokenBalance> findByUserIdAndToken(UUID userId, String token);
}
