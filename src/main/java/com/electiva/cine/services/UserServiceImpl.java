package com.electiva.cine.services;

import com.electiva.cine.dto.ServiceResponseDto;
import com.electiva.cine.dto.UserDto;
import com.electiva.cine.entity.RoleEntity;
import com.electiva.cine.entity.UserEntity;
import com.electiva.cine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity findByNick(String nick){
        return userRepository.findByNick(nick);
    }

    @Override
    public ServiceResponseDto createAdminUser(UserDto userDto) {
        ServiceResponseDto serviceResponseDto = new ServiceResponseDto();
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userDto.getName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setNick(userDto.getNick());

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        userEntity.setPass(bcrypt.encode(userDto.getPass()));

        RoleEntity role = new RoleEntity();
        role.setDescription("ROLE_ADMIN");
        role.setId(Long.valueOf(1));

        userEntity.setRoles(role);

        try{
            userRepository.save(userEntity);
            serviceResponseDto.setMessage("Usuario creado con exito!");
            serviceResponseDto.setCode("OK");
        }catch(Exception e){
            System.err.println("Error: "+e);
            serviceResponseDto.setMessage("Error al crear usuario");
            serviceResponseDto.setCode("ERROR_SAVED");
        }
        return serviceResponseDto;
    }

    private boolean checkIfUserExists(UserDto userDto){
        boolean isExists = false;
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userDto.getId());
        if(optionalUserEntity.isPresent()){
            isExists = true;
        }
        return isExists;
    }
}
