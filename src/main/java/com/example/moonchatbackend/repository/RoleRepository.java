package com.example.moonchatbackend.repository;

import com.example.moonchatbackend.model.users.Role;
import com.example.moonchatbackend.model.users.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByName(Role name);
}