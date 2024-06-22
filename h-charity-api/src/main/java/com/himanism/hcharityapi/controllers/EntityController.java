package com.himanism.hcharityapi.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.himanism.hcharityapi.dto.request.EntityRequestDto;
import com.himanism.hcharityapi.entities.Entities;
import com.himanism.hcharityapi.entities.EntityBankDetails;
import com.himanism.hcharityapi.entities.EntityPhotos;
import com.himanism.hcharityapi.security.services.UserDetailsImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.himanism.hcharityapi.services.EntityService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/entity")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public class EntityController {

    private final EntityService entityService;

    @GetMapping("")
    public List<Entities> getEntities(Authentication authentication) {
            // log.info("UsersController: List users");
            List<Entities> entities = entityService.getEntities(authentication);
        return entities;
    }

    @GetMapping("/{entityId}")
    public Optional<Entities> getEntityById(@PathVariable Long entityId) {
        // if (user.getEmail() == null || user.getEmail().isEmpty() ||
        // user.getPassword() == null || user.getPassword().isEmpty()) {
        // throw new AppException("All fields are required.", HttpStatus.BAD_REQUEST);
        // }
        return entityService.getEntityById(entityId);
    }

    @PostMapping("")
    public Entities addEntity(Authentication authentication, @Valid @RequestBody EntityRequestDto entityDto) {
        // if (user.getEmail() == null || user.getEmail().isEmpty() ||
        // user.getPassword() == null || user.getPassword().isEmpty()) {
        // throw new AppException("All fields are required.", HttpStatus.BAD_REQUEST);
        // }

        // Make proper use of Lombok validators

        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
   
        String username = ((UserDetailsImpl) principle).getUsername();
        entityDto.setCreatedBy(username);

        return entityService.addEntity(entityDto);
    }

    @PutMapping("")
    public Entities updateEntity(@Valid @RequestBody EntityRequestDto entityDto) {
        // if (user.getEmail() == null || user.getEmail().isEmpty() ||
        // user.getPassword() == null || user.getPassword().isEmpty()) {
        // throw new AppException("All fields are required.", HttpStatus.BAD_REQUEST);
        // }

        return entityService.updateEntity(entityDto);
    }

    @DeleteMapping("/{entityId}")
    public void deleteEntity(@PathVariable Long entityId) {
        // if (user.getEmail() == null || user.getEmail().isEmpty() ||
        // user.getPassword() == null || user.getPassword().isEmpty()) {
        // throw new AppException("All fields are required.", HttpStatus.BAD_REQUEST);
        // }

        entityService.deleteEntity(entityId);
    }

    @GetMapping("/bankDetails/{entityId}")
    public Optional<EntityBankDetails> getBankDetailsByEntityId(@PathVariable Long entityId) {
        // if (user.getEmail() == null || user.getEmail().isEmpty() ||
        // user.getPassword() == null || user.getPassword().isEmpty()) {
        // throw new AppException("All fields are required.", HttpStatus.BAD_REQUEST);
        // }

        Optional<EntityBankDetails> obj = entityService.getBankDetailsByEntityId(entityId);

        return obj;
    }

    @GetMapping("/photos/{entityId}")
    public Optional<EntityPhotos> getPhotosByEntityId(@PathVariable Long entityId) {
        Optional<EntityPhotos> obj = entityService.getPhotosByEntityId(entityId);
        return obj;
    }

}
