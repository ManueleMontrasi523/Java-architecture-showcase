package it.marketplace.service.impl;

import it.marketplace.common.dto.UserDto;
import it.marketplace.common.enums.RoleEnum;
import it.marketplace.common.enums.StatusUserEnum;
import it.marketplace.common.exception.ServiceException;
import it.marketplace.repository.UserRepository;
import it.marketplace.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

import static it.marketplace.common.exception.ServiceException.ErrorCode.*;
import static it.marketplace.utils.CopyProperties.copyNonNullProperties;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Service implementation for managing users in the marketplace system.
 * Handles user creation, update, deletion, status changes, and retrieval operations.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    private UserRepository repository;

    /**
     * Saves a new user.
     *
     * @param dto the user DTO to save
     *
     * @throws ServiceException if the user already exists or another error occurs
     */
    @Override
    public void save(UserDto dto) throws ServiceException {
        try {
            UserDto entityOld = repository.findByEmailIgnoreCase(dto.getEmail());
            if (nonNull(entityOld)) throw new ServiceException(DATA_ALREADY_PRESENT, "Email already registered");

            dto.setRole(RoleEnum.CLIENT);
            dto.setStatus(StatusUserEnum.ACTIVE);
            dto.setTmsSubscriptionDate(nonNull(dto.getTmsSubscriptionDate()) ? dto.getTmsSubscriptionDate() : LocalDateTime.now());
            dto.setTmsUpdate(LocalDateTime.now());

            repository.save(dto);
        } catch (ServiceException e) {
            logger.error("ERROR in the class {} with error {}", this.getClass().getName(), e.fillInStackTrace());
            throw new ServiceException(GENERIC_ERROR, e.getMessage());
        }
    }

    /**
     * Saves a list of users.
     *
     * @param dtos the list of user DTOs to save
     *
     * @throws ServiceException if any user already exists or another error occurs
     */
    @Override
    public void saveAll(List<UserDto> dtos) {
        try {
            LocalDateTime now = LocalDateTime.now();
            List<String> emails = dtos.stream().map(UserDto::getEmail).toList();
            List<UserDto> entityOld = repository.findAllByEmailIgnoreCaseIn(emails);
            if (!CollectionUtils.isEmpty(entityOld))
                throw new ServiceException(DATA_ALREADY_PRESENT, "Email already registered");

            dtos.forEach(d -> {
                d.setRole(RoleEnum.CLIENT);
                d.setStatus(StatusUserEnum.ACTIVE);
                d.setTmsSubscriptionDate(now);
                d.setTmsUpdate(now);
            });

            repository.saveAll(dtos);
        } catch (ServiceException e) {
            logger.error("ERROR in the class {} with error {}", this.getClass().getName(), e.fillInStackTrace());
            throw new ServiceException(GENERIC_ERROR, e.getMessage());
        }
    }

    /**
     * Finds a user by email.
     *
     * @param email the email of the user to find
     *
     * @return the matching UserDto
     *
     * @throws ServiceException if the user is not found
     */
    @Override
    public UserDto findByEmail(String email) throws ServiceException {
        return checkIfUserExist(email);
    }

    /**
     * Finds all users by status.
     *
     * @param status the status to filter users
     *
     * @return a list of UserDto
     */
    @Override
    public List<UserDto> findAll(StatusUserEnum status) {
        return repository.findAllByStatus(status);
    }

    /**
     * Updates an existing user.
     *
     * @param dto the user DTO with updated data
     *
     * @throws ServiceException if the user is not found or another error occurs
     */
    @Override
    public void update(UserDto dto) throws ServiceException {
        UserDto entity = checkIfUserExist(dto.getEmail());

        copyNonNullProperties(dto, entity);
        entity.setTmsUpdate(LocalDateTime.now());
        repository.save(entity);
    }

    /**
     * Deletes a user by email.
     *
     * @param email the email of the user to delete
     *
     * @throws ServiceException if the user is not found or another error occurs
     */
    @Override
    public void deleteByEmail(String email) throws ServiceException {
        try {
            repository.deleteById(checkIfUserExist(email).getId());
        } catch (ServiceException e) {
            throw new ServiceException(GENERIC_ERROR, e.getMessage());
        }
    }

    /**
     * Updates the status of a user by email.
     *
     * @param email  the email of the user to update
     * @param status the new status to set
     *
     * @throws ServiceException if the user is not found or another error occurs
     */
    @Override
    public void statusByEmail(String email, StatusUserEnum status) throws ServiceException {
        checkIfUserExist(email);
        repository.statusRelationshipsByEmail(email, status);
    }

    /**
     * Checks if a user exists by email, throws exception if not found.
     *
     * @param email the email to check
     *
     * @return the matching UserDto
     *
     * @throws ServiceException if the user is not found
     */
    private UserDto checkIfUserExist(String email) throws ServiceException {
        UserDto entity = repository.findByEmailIgnoreCase(email);
        if (isNull(entity))
            throw new ServiceException(EMAIL_NOT_FOUND, "User with email: " + email + " not found");
        return entity;
    }
}
