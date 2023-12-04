package com.example.archvizarena.service;

import com.example.archvizarena.model.service.ReportSubmitServiceModel;
import com.example.archvizarena.model.view.ReportViewModel;

import java.util.List;

public interface ReportService {
    void saveProjectReport(ReportSubmitServiceModel report);

    void saveUserReport(ReportSubmitServiceModel report);

    List<ReportViewModel> findAllActiveUserReports();

    List<ReportViewModel> findAllActiveProjectReports();

    void dismissReport(Long id);

    ReportViewModel findById(Long id);

    void deleteById(Long reportId);

    void clearArchivedReports();

}
