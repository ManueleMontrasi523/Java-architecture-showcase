package it.marketplace.service;

import it.marketplace.common.dto.UserDto;
import it.marketplace.common.enums.StatusUserEnum;
import it.marketplace.common.exception.ServiceException;

import java.util.List;

/**
 * Service interface for managing users in the marketplace system.
 * Provides methods for user creation, update, deletion, status changes, and retrieval operations.
 */
public interface UserService {

    /**
     * Saves a new user.
     * @param dto the user DTO to save
     * @throws ServiceException if the user already exists or another error occurs
     */
    void save(UserDto dto) throws ServiceException;

    /**
     * Saves a list of users.
     * @param dtos the list of user DTOs to save
     */
    void saveAll(List<UserDto> dtos);

    /**
     * Finds a user by email.
     * @param email the email of the user to find
     * @return the matching UserDto
     * @throws ServiceException if the user is not found
     */
    UserDto findByEmail(String email) throws ServiceException;

    /**
     * Finds all users by status.
     * @param status the status to filter users
     * @return a list of UserDto
     * @throws ServiceException if an error occurs
     */
    List<UserDto> findAll(StatusUserEnum status) throws ServiceException;

    /**
     * Updates an existing user.
     * @param dto the user DTO with updated data
     * @throws ServiceException if the user is not found or another error occurs
     */
    void update(UserDto dto) throws ServiceException;

    /**
     * Deletes a user by email.
     * @param email the email of the user to delete
     * @throws ServiceException if the user is not found or another error occurs
     */
    void deleteByEmail(String email) throws ServiceException;

    /**
     * Updates the status of a user by email.
     * @param email the email of the user to update
     * @param status the new status to set
     * @throws ServiceException if the user is not found or another error occurs
     */
    void statusByEmail(String email, StatusUserEnum status) throws ServiceException;

}
