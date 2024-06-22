package com.himanism.hcharityapi.services.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.himanism.hcharityapi.controllers.FilesController;
import com.himanism.hcharityapi.entities.Entities;
import com.himanism.hcharityapi.entities.EntityPhotos;
import com.himanism.hcharityapi.repo.EntityPhotosRepository;
import com.himanism.hcharityapi.repo.EntityRepository;
import com.himanism.hcharityapi.services.FilesStorageService;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

  @Autowired
  EntityPhotosRepository entityPhotosRepository;

  @Autowired
  EntityRepository entityRepository;

  private final Path root = Paths.get("uploads");

  @Override
  public void init() {
    try {
      Files.createDirectories(root);
    } catch (IOException e) {
      throw new RuntimeException("Could not initialize folder for upload!");
    }
  }

  @Override
  public void save(MultipartFile file, Long entityId) {
    try {
      Path imagePath = Paths.get("uploads/" + entityId);
      if(!Files.exists(imagePath)) {
        Files.createDirectories(imagePath);
      } else {
        Files.copy(file.getInputStream(), imagePath.resolve(file.getOriginalFilename()));
        this.updatePhotosTableWithUrl(file, entityId);
      }
    } catch (Exception e) {
      if (e instanceof FileAlreadyExistsException) {
        throw new RuntimeException("A file of that name already exists.");
      }
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  public Resource load(String filename) {
    try {
      Path imagePath = Paths.get("uploads/" + 5);
      Path file = imagePath.resolve(filename);
      Resource resource = new UrlResource(file.toUri());

      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new RuntimeException("Could not read the file!");
      }
    } catch (MalformedURLException e) {
      throw new RuntimeException("Error: " + e.getMessage());
    }
  }

  @Override
  public boolean delete(String filename) {
    try {
      Path file = root.resolve(filename);
      return Files.deleteIfExists(file);
    } catch (IOException e) {
      throw new RuntimeException("Error: " + e.getMessage());
    }
  }

  @Override
  public void deleteAll() {
    FileSystemUtils.deleteRecursively(root.toFile());
  }

  @Override
  public Stream<Path> loadAll(Long entityId) {
    try {
      Path photosPath = Paths.get("uploads/" + entityId);
      return Files.walk(photosPath, 1).filter(path -> !path.equals(photosPath)).map(photosPath::relativize);
    } catch (IOException e) {
      throw new RuntimeException("Could not load the files!");
    }
  }

  @Override
  public Stream<Path> loadAll() {
    try {
      Path photosPath = Paths.get("uploads/");
      return Files.walk(photosPath, 1).filter(path -> !path.equals(photosPath)).map(photosPath::relativize);
    } catch (IOException e) {
      throw new RuntimeException("Could not load the files!");
    }
  }

  @Override
  public void saveQRCode(Long entityId, MultipartFile file) {
    try {
      Path qrcodePath = Paths.get("uploads/qrcodes/" + entityId);
      if(!Files.exists(qrcodePath)) {
        Files.createDirectories(qrcodePath);
      } else {
        this.cleanDirectory("uploads/qrcodes/" + entityId);
      }
      Files.copy(file.getInputStream(), qrcodePath.resolve(file.getOriginalFilename()));

      this.loadAllByPath(qrcodePath).forEach(path -> {
        String filename = path.getFileName().toString();
        String url = MvcUriComponentsBuilder
            .fromMethodName(FilesController.class, "getFile", filename).build().toString();
  
        if(path.getFileName().toString().equalsIgnoreCase(file.getOriginalFilename())) {
          saveEntityPhotos(url, entityId, true);
        }
      });

    } catch (Exception e) {
      if (e instanceof FileAlreadyExistsException) {
        throw new RuntimeException("A file of that name already exists.");
      }

      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  public void saveCoverPhoto(Long entityId, MultipartFile file) {
    try {
      Path coverPath = Paths.get("uploads/cover/" + entityId);
      if(!Files.exists(coverPath)) {
        Files.createDirectories(coverPath);
      } else {
        this.cleanDirectory("uploads/cover/" + entityId);
      }
      Files.copy(file.getInputStream(), coverPath.resolve(file.getOriginalFilename()));

      this.loadAllByPath(coverPath).forEach(path -> {
        String filename = path.getFileName().toString();
        String url = MvcUriComponentsBuilder
            .fromMethodName(FilesController.class, "getFile", filename).build().toString();
  
        if(path.getFileName().toString().equalsIgnoreCase(file.getOriginalFilename())) {
          saveEntityPhotos(url, entityId, false);
        }
      });

    } catch (Exception e) {
      if (e instanceof FileAlreadyExistsException) {
        throw new RuntimeException("A file of that name already exists.");
      }

      throw new RuntimeException(e.getMessage());
    }
  }

  private void cleanDirectory(String path) {
    File directory = new File(path);
    for (File fileObj: Objects.requireNonNull(directory.listFiles())) {
      if (!fileObj.isDirectory()) {
        fileObj.delete();
      }
    }
  }

  private void updatePhotosTableWithUrl(MultipartFile file, Long entityId) {
    this.loadAll(entityId).forEach(path -> {
      String filename = path.getFileName().toString();
      String url = MvcUriComponentsBuilder
          .fromMethodName(FilesController.class, "getFile", filename).build().toString();

      if(path.getFileName().toString().equalsIgnoreCase(file.getOriginalFilename())) {
        saveEntityPhotos(url, entityId, false);
      }
    });
  }

  private void saveEntityPhotos(String url, Long entityId, Boolean isQRCode) {
    EntityPhotos entityPhotos = new EntityPhotos();
    entityPhotos.setIsQRCode(isQRCode);
    entityPhotos.setPhotoUrl(url);
    entityPhotos.setEntityId(entityId);
    entityPhotosRepository.save(entityPhotos);
  }

  private Stream<Path> loadAllByPath(Path photosPath) {
    try {
      return Files.walk(photosPath, 1).filter(path -> !path.equals(photosPath)).map(photosPath::relativize);
    } catch (IOException e) {
      throw new RuntimeException("Could not load the files!");
    }
  }
}
