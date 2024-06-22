package com.himanism.hcharityapi.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.himanism.hcharityapi.dto.request.EntityRequestDto;
import com.himanism.hcharityapi.entities.Entities;
import com.himanism.hcharityapi.entities.EntityBankDetails;
import com.himanism.hcharityapi.entities.EntityPhotos;
import com.himanism.hcharityapi.repo.EntityBankDetailsRepo;
import com.himanism.hcharityapi.repo.EntityPhotosRepository;
import com.himanism.hcharityapi.repo.EntityRepository;
import com.himanism.hcharityapi.services.EntityService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EntityServiceImpl implements EntityService {

    private final EntityRepository entityRepository;
    private final EntityBankDetailsRepo bankDetailsRepo;
    private final EntityPhotosRepository photosRepository;

    @Override
    public List<Entities> getEntities(Authentication authentication) {
        List<Entities> entities = entityRepository.findAll();
        return entities;
    }

    @Override
    public Entities addEntity(EntityRequestDto entityRequestDto) {
        Entities entity = new Entities();
        entity.setName(entityRequestDto.getName());
        entity.setType(entityRequestDto.getType());
        entity.setPresident(entityRequestDto.getPresident());
        entity.setPoc(entityRequestDto.getPoc());
        entity.setDescription(entityRequestDto.getDescription());
        entity.setIsVerified(entityRequestDto.getIsVerified());
        entity.setHasInternet(entityRequestDto.getHasInternet());        
        entity.setMobile(entityRequestDto.getMobile());
        entity.setOffice(entityRequestDto.getOffice());        
        entity.setCreatedBy(entityRequestDto.getCreatedBy());
        entity.setCreatedDate(new Date());
        entity.setAddress(entityRequestDto.getAddress());
        return entityRepository.save(entity);
    }

    @Override
    public Entities updateEntity(EntityRequestDto entityRequestDto) {
        Optional<Entities> existingEntity = entityRepository.findById(entityRequestDto.getId());
        if (existingEntity.isEmpty()) throw new IllegalArgumentException("Invalid Entity ID");
        Entities updatedEntity = existingEntity.get();

        updatedEntity.setName(entityRequestDto.getName());
        updatedEntity.setType(entityRequestDto.getType());
        updatedEntity.setPresident(entityRequestDto.getPresident());
        updatedEntity.setPoc(entityRequestDto.getPoc());
        updatedEntity.setDescription(entityRequestDto.getDescription());
        updatedEntity.setIsVerified(entityRequestDto.getIsVerified());
        updatedEntity.setHasInternet(entityRequestDto.getHasInternet());        
        updatedEntity.setMobile(entityRequestDto.getMobile());
        updatedEntity.setOffice(entityRequestDto.getOffice());        
        updatedEntity.setUpdatedBy("Admin");
        updatedEntity.setUpdatedDate(new Date());          

        return entityRepository.save(updatedEntity);
    }

    @Override
    public void deleteEntity(Long entityId) {
         entityRepository.deleteById(entityId);
    }

    @Override
    public Optional<Entities> getEntityById(Long entityId) {
        Optional<Entities> optional = entityRepository.findById(entityId);
        return optional;
    }

    @Override
    public Optional<EntityBankDetails> getBankDetailsByEntityId(Long entityId) {
        return bankDetailsRepo.findByEntityId(entityId);
    }

    @Override
    public Optional<EntityPhotos> getPhotosByEntityId(Long entityId) {
        return photosRepository.findByEntityId(entityId);
    }
}
