package com.example.archvizarena.repository;

import com.example.archvizarena.model.binding.ProjectSearchBindingModel;
import com.example.archvizarena.model.entity.PortfolioProjectEntity;
import com.example.archvizarena.model.entity.UserEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProjectSpecification implements Specification<PortfolioProjectEntity> {

    private final ProjectSearchBindingModel projectSearchBindingModel;

    public ProjectSpecification(ProjectSearchBindingModel projectSearchBindingModel) {
        this.projectSearchBindingModel = projectSearchBindingModel;
    }


    @Override
    public Predicate toPredicate(Root<PortfolioProjectEntity> root,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder criteriaBuilder) {

        final List<Predicate> predicates = new ArrayList<>();

        if (projectSearchBindingModel.getCountry() != null && !projectSearchBindingModel.getCountry().isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("country"), projectSearchBindingModel.getCountry()));
        }

        if (projectSearchBindingModel.getCreatorType() != null) {
            predicates.add(criteriaBuilder.equal(root.get("creatorType"), projectSearchBindingModel.getCreatorType()));
        }
        if (projectSearchBindingModel.getCategory() != null) {
            predicates.add(criteriaBuilder.equal(root.get("category"), projectSearchBindingModel.getCategory()));
        }


        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
