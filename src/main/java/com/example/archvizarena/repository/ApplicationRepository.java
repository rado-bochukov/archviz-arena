package com.example.archvizarena.repository;

import com.example.archvizarena.model.entity.ApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity,Long> {

    List<ApplicationEntity> findByJobPublication_Id(Long id);
}
