package com.example.archvizarena.testUtils;

import com.example.archvizarena.model.entity.PictureEntity;
import com.example.archvizarena.model.entity.PortfolioProjectEntity;
import com.example.archvizarena.model.entity.UserEntity;
import com.example.archvizarena.model.entity.enums.ProjectCategoryEnum;
import com.example.archvizarena.repository.PictureRepository;
import com.example.archvizarena.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestDataUtil {

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public List<PictureEntity> createTestPictures() {
        PictureEntity picture1 = new PictureEntity();
        PictureEntity picture2 = new PictureEntity();

        picture1.setUrl("picture1");
        picture2.setUrl("picture2");

        return pictureRepository.saveAll(List.of(picture1, picture2));
    }

    public PortfolioProjectEntity createTestProject(UserEntity owner) {



        PortfolioProjectEntity project = new PortfolioProjectEntity();
        project.setTitle("Test Project");
        project.setCategory(ProjectCategoryEnum.EXTERIOR);
        project.setDescription("Test project description");
        project.setAuthor(owner);
        project.setActive(true);


        return projectRepository.save(project);
    }

    public void cleanUp(){
        projectRepository.deleteAll();
        pictureRepository.deleteAll();
    }


}
