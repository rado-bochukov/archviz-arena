package com.example.archvizarena.repository;

import com.example.archvizarena.model.entity.UserRole;
import com.example.archvizarena.model.entity.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    UserRole findByRole(UserRoleEnum role);
}
