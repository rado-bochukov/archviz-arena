package com.example.archvizarena.repository;

import com.example.archvizarena.model.entity.JobPublicationEntity;
import com.example.archvizarena.model.entity.PortfolioProjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPublicationRepository extends JpaRepository<JobPublicationEntity,Long>,
        JpaSpecificationExecutor<JobPublicationEntity> {

    List<JobPublicationEntity> findAllByBuyer_Id(Long id);

    Page<JobPublicationEntity> findAllByIsActiveTrue(Pageable pageable);
}
