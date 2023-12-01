package com.example.archvizarena.service;

import com.example.archvizarena.model.service.ReportSubmitServiceModel;
import com.example.archvizarena.model.view.ReportedUserViewModel;

import java.util.List;

public interface ReportService {
    void saveProjectReport(ReportSubmitServiceModel report);

    void saveUserReport(ReportSubmitServiceModel report);

    List<ReportedUserViewModel> findAllUserReports();

}
