package com.blockbank.blockbank.repository;

import com.blockbank.blockbank.entity.StakePosition;
import com.blockbank.blockbank.entity.enums.LendingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface StakePositionRepository extends JpaRepository<StakePosition, UUID> {
    List<StakePosition> findAllByUserId(UUID userId);

    Optional<StakePosition> findByUserIdAndIsActiveTrue(UUID userId);
}
