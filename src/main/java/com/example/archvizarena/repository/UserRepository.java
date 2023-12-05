package com.example.archvizarena.repository;

import com.example.archvizarena.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>,
        JpaSpecificationExecutor<UserEntity> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    @Query("select u from UserEntity u where u.isMuted=true ")
    List<UserEntity> findAllByIsMutedTrue();
    @Query("select u from UserEntity u where u.userOccupationEnum='ARTIST' ")
    Page<UserEntity> findAllByUserOccupation_Artist(Pageable pageable);
}
