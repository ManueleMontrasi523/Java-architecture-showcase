package it.marketplace.microservices.service.impl;

import it.marketplace.microservices.common.dto.UserDto;
import it.marketplace.microservices.common.enums.RoleEnum;
import it.marketplace.microservices.common.enums.StatusUserEnum;
import it.marketplace.microservices.config.exception.ServiceException;
import it.marketplace.microservices.config.mapper.UserMapper;
import it.marketplace.microservices.database.entity.UserEntity;
import it.marketplace.microservices.database.repository.UserRepository;
import it.marketplace.microservices.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static it.marketplace.microservices.config.exception.ServiceException.ErrorCode.*;
import static it.marketplace.microservices.config.mapper.UserMapper.toDto;
import static it.marketplace.microservices.config.mapper.UserMapper.toEntity;
import static it.marketplace.microservices.utils.CopyProperties.copyNonNullProperties;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(UserDto dto) throws ServiceException {
        try {
            UserEntity entityOld = userRepository.findByEmailIgnoreCase(dto.getEmail());
            if (nonNull(entityOld)) throw new ServiceException(DATA_ALREADY_PRESENT, "Email already registered");

            UserEntity entity = toEntity(dto);

            entity.setRole(RoleEnum.CLIENT);
            entity.setStatus(StatusUserEnum.ACTIVE);
            entity.setTmsSubscriptionDate(nonNull(dto.getTmsSubscriptionDate()) ? dto.getTmsSubscriptionDate() : LocalDateTime.now());
            entity.setTmsUpdate(LocalDateTime.now());

            userRepository.save(entity);
        } catch (ServiceException e) {
            logger.error("ERROR in the class {} with error {}", this.getClass().getName(), e.fillInStackTrace());
            throw new ServiceException(GENERIC_ERROR, e.getMessage());
        }
    }

    @Override
    public UserDto findByEmail(String email) throws ServiceException {
        UserEntity entity = checkIfUserExist(email);
        return toDto(entity);
    }

    @Override
    public UserEntity findByEmailEntity(String email) throws ServiceException {
        return checkIfUserExist(email);
    }

    @Override
    public List<UserDto> findAll(StatusUserEnum status) {
        List<UserEntity> entities = userRepository.findAllByStatus(status);
        List<UserDto> dtos = new ArrayList<>(entities.stream()
                .map(UserMapper::toDto)
                .toList());
        return dtos;
    }

    @Override
    public void update(UserDto dto) throws ServiceException {
        UserEntity entity = checkIfUserExist(dto.getEmail());

        copyNonNullProperties(dto, entity);
        entity.setTmsUpdate(LocalDateTime.now());
        userRepository.save(entity);
    }

    @Override
    public void deleteByEmail(String email) throws ServiceException {
        try {
            UserEntity entity = checkIfUserExist(email);
            userRepository.deleteById(entity.getId());
        } catch (ServiceException e) {
            throw new ServiceException(GENERIC_ERROR, e.getMessage());
        }
    }

    @Override
    public void statusByEmail(String email, StatusUserEnum status) throws ServiceException {
        checkIfUserExist(email);
        userRepository.statusRelationshipsByEmail(email, status);
    }

    private UserEntity checkIfUserExist(String email) throws ServiceException {
        UserEntity entity = userRepository.findByEmailIgnoreCase(email);
        if (isNull(entity))
            throw new ServiceException(EMAIL_NOT_FOUND, "User with email: " + email + " not found");
        return entity;
    }
}
