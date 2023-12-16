package com.example.archvizarena.repository.specification;

import com.example.archvizarena.model.binding.JobPublicationSearchBindingModel;
import com.example.archvizarena.model.entity.JobPublicationEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class JobSpecification implements Specification<JobPublicationEntity> {

    private final JobPublicationSearchBindingModel jobPublicationSearchBindingModel;

    public JobSpecification(JobPublicationSearchBindingModel jobPublicationSearchBindingModel) {
        this.jobPublicationSearchBindingModel = jobPublicationSearchBindingModel;
    }

    @Override
    public Predicate toPredicate(Root<JobPublicationEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = new ArrayList<>();

        if (jobPublicationSearchBindingModel.getCountry() != null && !jobPublicationSearchBindingModel.getCountry().isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.join("buyer").get("country"), jobPublicationSearchBindingModel.getCountry()));
        }
        if (jobPublicationSearchBindingModel.getPriceRange() != null) {

            switch (jobPublicationSearchBindingModel.getPriceRange()) {
                case "range1" -> predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("budget"), 1000));
                case "range2" -> {
                    predicates.add(criteriaBuilder.greaterThan(root.get("budget"), 1000));
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("budget"), 3000));
                }
                case "range3" -> predicates.add(criteriaBuilder.greaterThan(root.get("budget"), 3000));
            }
        }
        if (jobPublicationSearchBindingModel.getCategory() != null) {
            predicates.add(criteriaBuilder.equal(root.get("category"), jobPublicationSearchBindingModel.getCategory()));
        }


        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
