package com.blockbank.blockbank.repository;

import com.blockbank.blockbank.entity.SwapTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface SwapTransactionRepository extends JpaRepository<SwapTransaction, UUID> {
    List<SwapTransaction> findAllByUserIdOrderByCreatedAtDesc(UUID userId);
}
