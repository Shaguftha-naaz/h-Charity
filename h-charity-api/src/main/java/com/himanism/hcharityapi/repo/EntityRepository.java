package com.himanism.hcharityapi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.himanism.hcharityapi.entities.Entities;

@Repository
public interface EntityRepository extends JpaRepository<Entities, Long> {}
