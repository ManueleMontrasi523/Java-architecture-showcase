package it.marketplace.microservices.service;

import it.marketplace.microservices.common.dto.UserDto;
import it.marketplace.microservices.common.enums.StatusUserEnum;
import it.marketplace.microservices.common.exception.UserServiceException;

import java.util.List;

public interface UserService {

    void save(UserDto dto) throws UserServiceException;

    UserDto findByEmail(String username) throws UserServiceException;

    List<UserDto> findAll() throws UserServiceException;

    void update(UserDto dto) throws UserServiceException;

    void deleteByEmail(String username) throws UserServiceException;

    void statusByEmail(String username, StatusUserEnum status) throws UserServiceException;

}
