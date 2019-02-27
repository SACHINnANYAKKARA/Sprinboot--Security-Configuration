package com.hospital.jwtauthentication.repository;

import java.util.Optional;

import com.hospital.jwtauthentication.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hospital.jwtauthentication.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}