package com.example.archvizarena.repository;

import com.example.archvizarena.model.entity.PortfolioProjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<PortfolioProjectEntity, Long>,
        JpaSpecificationExecutor<PortfolioProjectEntity> {

    List<PortfolioProjectEntity> findAllByAuthor_Id(Long id);
    Page<PortfolioProjectEntity> findAllByIsActiveTrue(Pageable pageable);



}
