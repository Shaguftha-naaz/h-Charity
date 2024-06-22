package com.himanism.hcharityapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;

import com.himanism.hcharityapi.dto.request.EntityRequestDto;
import com.himanism.hcharityapi.entities.Entities;
import com.himanism.hcharityapi.entities.EntityBankDetails;
import com.himanism.hcharityapi.entities.EntityPhotos;

public interface EntityService {
    List<Entities> getEntities(Authentication authentication);

    Entities addEntity(EntityRequestDto EntityDto);

    Entities updateEntity(EntityRequestDto EntityDto);

    void deleteEntity(Long entityId);

    Optional<Entities> getEntityById(Long entityId);

    Optional<EntityBankDetails> getBankDetailsByEntityId(Long entityId);
    Optional<EntityPhotos> getPhotosByEntityId(Long entityId);
}
