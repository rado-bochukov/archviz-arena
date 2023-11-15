package com.example.archvizarena.repository;

import com.example.archvizarena.model.entity.PortfolioProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<PortfolioProjectEntity, Long> {
}
