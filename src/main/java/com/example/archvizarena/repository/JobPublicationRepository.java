package com.example.archvizarena.repository;

import com.example.archvizarena.model.entity.JobPublicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPublicationRepository extends JpaRepository<JobPublicationEntity,Long> {

    List<JobPublicationEntity> findAllByBuyer_Id(Long id);
}
