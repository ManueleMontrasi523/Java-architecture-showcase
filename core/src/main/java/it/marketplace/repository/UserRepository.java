package it.marketplace.repository;

import it.marketplace.common.dto.UserDto;
import it.marketplace.common.enums.StatusUserEnum;

import java.util.List;

public interface UserRepository {

    void save(UserDto dto);

    void saveAll(List<UserDto> dtos);

    /**
     * Finds a user by email, ignoring case.
     *
     * @param email the user's email
     *
     * @return the matching UserDto, or null if not found
     */
    UserDto findByEmailIgnoreCase(String email);

    /**
     * Finds all users by a list of emails, ignoring case.
     *
     * @param email the list of user emails
     *
     * @return a list of UserDto
     */
    List<UserDto> findAllByEmailIgnoreCaseIn(List<String> email);

    /**
     * Finds all users by status.
     *
     * @param status the user status
     *
     * @return a list of UserDto
     */
    List<UserDto> findAllByStatus(StatusUserEnum status);

    /**
     * Updates the status and update timestamp of a user by email (case-insensitive).
     *
     * @param email  the user's email
     * @param status the new status to set
     */
    void statusRelationshipsByEmail(String email, StatusUserEnum status);

    void deleteById(Long id);
}
