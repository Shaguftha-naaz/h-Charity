package com.himanism.hcharityapi.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.himanism.hcharityapi.entities.EntityPhotos;

@Repository
public interface EntityPhotosRepository extends JpaRepository<EntityPhotos, Long> {
    Optional<EntityPhotos> findByEntityId(Long entityId);
}
