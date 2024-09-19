package com.pratyushsharma258.moonchatbackend.repository;

import com.pratyushsharma258.moonchatbackend.model.users.Role;
import com.pratyushsharma258.moonchatbackend.model.users.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByName(Role name);
}