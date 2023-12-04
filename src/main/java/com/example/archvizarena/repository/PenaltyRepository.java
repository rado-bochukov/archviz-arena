package com.example.archvizarena.repository;

import com.example.archvizarena.model.entity.PenaltyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PenaltyRepository extends JpaRepository<PenaltyEntity ,Long> {
}
