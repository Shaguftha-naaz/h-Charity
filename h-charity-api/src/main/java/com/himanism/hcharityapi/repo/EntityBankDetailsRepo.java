package com.himanism.hcharityapi.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.himanism.hcharityapi.entities.EntityBankDetails;

@Repository
public interface EntityBankDetailsRepo extends JpaRepository<EntityBankDetails, Long> {
    Optional<EntityBankDetails> findByEntityId(Long entityId);
}
