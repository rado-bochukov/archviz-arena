package com.example.archvizarena.repository;

import com.example.archvizarena.model.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<ReportEntity,Long> {

    List<ReportEntity> findAllByReportedProjectNull();
    List<ReportEntity> findAllByReportedProjectNotNull();
    List<ReportEntity> findAllByReportedUser_Id(Long id);

    List<ReportEntity> findAllByIsArchivedTrue();
}
