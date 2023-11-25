package com.example.archvizarena.repository;

import com.example.archvizarena.model.entity.WorkInProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkInProgressRepository extends JpaRepository<WorkInProgressEntity, Long> {
}
