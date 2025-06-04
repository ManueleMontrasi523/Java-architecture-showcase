package it.marketplace;

import it.marketplace.common.dto.UserDto;
import it.marketplace.common.enums.StatusUserEnum;
import it.marketplace.mapper.UserMapper;
import it.marketplace.repository.UserJpaRepository;
import it.marketplace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import static it.marketplace.mapper.UserMapper.toDto;
import static it.marketplace.mapper.UserMapper.toEntity;
import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * Repository interface for managing UserDto persistence operations.
 * Provides methods to find users by email, status, and update user status by email.
 */
@Repository
public class UserRepositoryAdapter implements UserRepository {

    @Autowired
    private UserJpaRepository repository;

    /**
     * Saves a user in the database.
     *
     * @param dto the user to save
     */
    @Override
    public void save(UserDto dto) {
        repository.save(toEntity(dto));
    }

    /**
     * Saves a list of users in the database.
     *
     * @param dtos the list of users to save
     */
    @Override
    public void saveAll(List<UserDto> dtos) {
        repository.saveAll(dtos.stream().map(UserMapper::toEntity).toList());
    }

    /**
     * Finds a user by email, ignoring case.
     *
     * @param email the user's email
     *
     * @return the matching UserDto, or null if not found
     */
    @Override
    public UserDto findByEmailIgnoreCase(String email) {
        return toDto(repository.findByEmailIgnoreCase(email.toLowerCase()));
    }

    /**
     * Finds all users by a list of emails, ignoring case.
     *
     * @param email the list of user emails
     *
     * @return a list of UserDto
     */
    @Override
    public List<UserDto> findAllByEmailIgnoreCaseIn(List<String> email) {
        return repository.findAllByEmailIgnoreCaseIn(email.stream().map(String::toLowerCase).toList())
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }

    /**
     * Finds all users by status.
     *
     * @param status the user status
     *
     * @return a list of UserDto
     */
    @Override
    public List<UserDto> findAllByStatus(StatusUserEnum status) {
        List<UserDto> dtos;
        if (isEmpty(status)) {
            dtos = repository.findAll()
                    .stream()
                    .map(UserMapper::toDto)
                    .toList();
        } else {
            dtos = repository.findAllByStatus(status)
                    .stream()
                    .map(UserMapper::toDto)
                    .toList();
        }
        return dtos;
    }

    /**
     * Updates the status and update timestamp of a user by email (case-insensitive).
     *
     * @param email  the user's email
     * @param status the new status to set
     */
    @Override
    public void statusRelationshipsByEmail(@Param("email") String email, @Param("status") StatusUserEnum status) {
        repository.statusRelationshipsByEmail(email.toLowerCase(), status);
    }

    /**
     * Deletes a user by its id.
     *
     * @param id the id of the user to delete
     */
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
