package com.example.archvizarena.repository;

import com.example.archvizarena.model.binding.ArtistSearchBindingModel;
import com.example.archvizarena.model.entity.UserEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ArtistSpecification implements Specification<UserEntity> {

    private final ArtistSearchBindingModel artistSearchBindingModel;

    public ArtistSpecification(ArtistSearchBindingModel artistSearchBindingModel) {
        this.artistSearchBindingModel = artistSearchBindingModel;
    }

    @Override
    public Predicate toPredicate(Root<UserEntity> root,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder criteriaBuilder) {

        final List<Predicate> predicates = new ArrayList<>();

        if (artistSearchBindingModel.getCountry() != null && !artistSearchBindingModel.getCountry().isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("country"), artistSearchBindingModel.getCountry()));
        }
        if (artistSearchBindingModel.getPriceRange() != null) {

            switch (artistSearchBindingModel.getPriceRange()) {
                case "range1" -> predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("pricePerImage"), 200));
                case "range2" -> {
                    predicates.add(criteriaBuilder.greaterThan(root.get("pricePerImage"), 200));
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("pricePerImage"), 500));
                }
                case "range3" -> predicates.add(criteriaBuilder.greaterThan(root.get("pricePerImage"), 500));
            }
        }
        if (artistSearchBindingModel.getCreatorType() != null) {
            predicates.add(criteriaBuilder.equal(root.get("creatorType"), artistSearchBindingModel.getCreatorType()));
        }


        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
