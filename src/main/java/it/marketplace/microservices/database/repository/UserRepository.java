package it.marketplace.microservices.database.repository;

import it.marketplace.microservices.common.enums.StatusUserEnum;
import it.marketplace.microservices.database.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmailIgnoreCase(String email);

    List<UserEntity> findAllByStatus(StatusUserEnum status);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET u.status = :status, u.tmsUpdate = CURRENT_TIMESTAMP WHERE LOWER(u.email) = LOWER(:email)")
    void statusRelationshipsByEmail(@Param("email") String email, @Param("status") StatusUserEnum status);

}
