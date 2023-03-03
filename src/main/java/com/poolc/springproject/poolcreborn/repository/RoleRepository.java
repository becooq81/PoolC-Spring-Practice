package com.poolc.springproject.poolcreborn.repository;

import com.poolc.springproject.poolcreborn.model.ERole;
import com.poolc.springproject.poolcreborn.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(ERole role);
}
