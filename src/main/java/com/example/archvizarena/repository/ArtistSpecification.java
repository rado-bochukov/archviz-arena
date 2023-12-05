package com.example.archvizarena.repository;

import com.example.archvizarena.model.binding.ArtistSearchBindingModel;
import com.example.archvizarena.model.entity.UserEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class ArtistSpecification implements Specification<UserEntity> {

    private final ArtistSearchBindingModel artistSearchBindingModel;

    public ArtistSpecification(ArtistSearchBindingModel artistSearchBindingModel) {
        this.artistSearchBindingModel = artistSearchBindingModel;
    }

    @Override
    public Predicate toPredicate(Root<UserEntity> root,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();

        if (artistSearchBindingModel.getCountry() != null && !artistSearchBindingModel.getCountry().isEmpty()) {
            predicate.getExpressions().add(
                    criteriaBuilder.and(criteriaBuilder.equal(root.get("country"), artistSearchBindingModel.getCountry()))
            );
        }

        if (artistSearchBindingModel.getPriceRange() != null) {

            switch (artistSearchBindingModel.getPriceRange()) {
                case "range1" -> predicate.getExpressions().add(
                        criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("pricePerImage"), 200))
                );
                case "range2" -> {
                    predicate.getExpressions().add(
                            criteriaBuilder.and(criteriaBuilder.greaterThan(root.get("pricePerImage"), 200))
                    );
                    predicate.getExpressions().add(
                            criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("pricePerImage"), 500))
                    );
                }
                case "range3" -> predicate.getExpressions().add(
                        criteriaBuilder.and(criteriaBuilder.greaterThan(root.get("pricePerImage"), 500))
                );
            }
        }
        if (artistSearchBindingModel.getCreatorType() != null ) {
            predicate.getExpressions().add(
                    criteriaBuilder.and(criteriaBuilder.equal(root.get("creatorType"), artistSearchBindingModel.getCreatorType()))
            );
        }

        System.out.println(predicate.toString());
        return predicate;
    }
}
