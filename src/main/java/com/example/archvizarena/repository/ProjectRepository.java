package com.example.archvizarena.repository;

import com.example.archvizarena.model.entity.PortfolioProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<PortfolioProjectEntity, Long> {

    List<PortfolioProjectEntity> findAllByAuthor_Id(Long id);
}
