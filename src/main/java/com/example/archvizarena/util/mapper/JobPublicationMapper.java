package com.example.archvizarena.util.mapper;

import com.example.archvizarena.model.entity.JobPublicationEntity;
import com.example.archvizarena.model.service.JobPublicationAddServiceModel;
import com.example.archvizarena.model.view.JobPublicationViewModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobPublicationMapper  {

    public  JobPublicationEntity mapFromJobServiceModel(JobPublicationAddServiceModel jobToBeAdded){
        JobPublicationEntity job=new JobPublicationEntity();
        job.setTitle(jobToBeAdded.getTitle());
        job.setDescription(jobToBeAdded.getDescription());
        job.setCategory(jobToBeAdded.getCategory());
        job.setBudget(jobToBeAdded.getBudget());

        return job;
    }

    public JobPublicationViewModel mapToJobViewModel(JobPublicationEntity jobPublicationEntity) {

        JobPublicationViewModel job = new JobPublicationViewModel();
        job.setId(jobPublicationEntity.getId());
        job.setCategory(jobPublicationEntity.getCategory());
        job.setTitle(jobPublicationEntity.getTitle());
        job.setDescription(jobPublicationEntity.getDescription());
        job.setAuthorName(jobPublicationEntity.getBuyer().getName());
        job.setAuthorId(jobPublicationEntity.getBuyer().getId());
        job.setBudget(jobPublicationEntity.getBudget());
        job.setAuthorCountry(jobPublicationEntity.getBuyer().getCountry());
        List<Long> applicantsIds = jobPublicationEntity.getApplications().stream()
                .map(applicationEntity -> applicationEntity.getApplicant().getId())
                .toList();
        job.setApplicantsId(applicantsIds);

        return job;
    }


}
