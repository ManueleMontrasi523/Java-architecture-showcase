package it.marketplace.repository;

import it.marketplace.common.enums.StatusUserEnum;
import it.marketplace.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository interface for managing UserDto persistence operations.
 * Provides methods to find users by email, status, and update user status by email.
 */
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    /**
     * Finds a user by email, ignoring case.
     *
     * @param email the user's email
     *
     * @return the matching UserEntity, or null if not found
     */
    UserEntity findByEmailIgnoreCase(String email);

    /**
     * Finds all users by a list of emails, ignoring case.
     *
     * @param email the list of user emails
     *
     * @return a list of UserEntity
     */
    List<UserEntity> findAllByEmailIgnoreCaseIn(List<String> email);

    /**
     * Finds all users by status.
     *
     * @param status the user status
     *
     * @return a list of UserEntity
     */
    List<UserEntity> findAllByStatus(StatusUserEnum status);

    /**
     * Updates the status and update timestamp of a user by email (case-insensitive).
     *
     * @param email  the user's email
     * @param status the new status to set
     */
    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET u.status = :status, u.tmsUpdate = CURRENT_TIMESTAMP WHERE LOWER(u.email) = LOWER(:email)")
    void statusRelationshipsByEmail(@Param("email") String email, @Param("status") StatusUserEnum status);
}
