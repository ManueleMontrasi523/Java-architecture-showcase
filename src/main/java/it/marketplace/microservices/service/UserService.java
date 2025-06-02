package it.marketplace.microservices.service;

import it.marketplace.microservices.common.dto.UserDto;
import it.marketplace.microservices.common.enums.StatusUserEnum;
import it.marketplace.microservices.config.exception.ServiceException;
import it.marketplace.microservices.database.entity.UserEntity;

import java.util.List;

public interface UserService {

    void save(UserDto dto) throws ServiceException;

    void saveAll(List<UserDto> dtos);

    UserDto findByEmail(String email) throws ServiceException;

    UserEntity findByEmailEntity(String email) throws ServiceException;

    List<UserDto> findAll(StatusUserEnum status) throws ServiceException;

    void update(UserDto dto) throws ServiceException;

    void deleteByEmail(String email) throws ServiceException;

    void statusByEmail(String email, StatusUserEnum status) throws ServiceException;

}
