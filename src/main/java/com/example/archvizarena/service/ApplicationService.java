package com.example.archvizarena.service;

import com.example.archvizarena.model.service.ApplicationAddServiceModel;
import com.example.archvizarena.model.view.ApplicationViewModel;

import java.util.List;

public interface ApplicationService {
    void saveApplication(ApplicationAddServiceModel applicationToBeAdded);

    List<ApplicationViewModel> findJobApplications(Long id);

}
