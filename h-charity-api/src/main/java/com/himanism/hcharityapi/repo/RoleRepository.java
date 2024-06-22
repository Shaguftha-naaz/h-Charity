package com.himanism.hcharityapi.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.himanism.hcharityapi.entities.Role;
import com.himanism.hcharityapi.models.Erole;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(Erole name);
}
