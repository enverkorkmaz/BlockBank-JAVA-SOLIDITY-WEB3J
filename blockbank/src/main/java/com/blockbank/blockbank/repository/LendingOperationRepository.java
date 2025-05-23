package com.blockbank.blockbank.repository;

import com.blockbank.blockbank.entity.LendingOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LendingOperationRepository extends JpaRepository<LendingOperation, UUID> {
    List<LendingOperation> findAllByUserIdOrderByCreatedAtDesc(UUID userId);
}
