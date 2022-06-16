package com.electiva.cine.services;

import com.electiva.cine.dto.ServiceResponseDto;
import com.electiva.cine.dto.UserDto;
import com.electiva.cine.entity.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> findAll();
    UserEntity findByNick(String nick);
    ServiceResponseDto createAdminUser(UserDto userDto);
}
