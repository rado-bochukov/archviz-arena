package com.example.archvizarena.util.mapper;

import com.example.archvizarena.model.entity.JobPublicationEntity;
import com.example.archvizarena.model.view.JobPublicationViewModel;

public interface JobPublicationMapper {

    JobPublicationViewModel mapToJobViewModel(JobPublicationEntity jobPublicationEntity);
}
