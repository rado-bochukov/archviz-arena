package com.example.archvizarena.repository;

import com.example.archvizarena.model.entity.ApplicationMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationMessageRepository extends JpaRepository<ApplicationMessageEntity,Long> {
}
