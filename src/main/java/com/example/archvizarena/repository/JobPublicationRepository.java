package com.example.archvizarena.repository;

import com.example.archvizarena.model.entity.JobPublication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPublicationRepository extends JpaRepository<JobPublication,Long> {
}
