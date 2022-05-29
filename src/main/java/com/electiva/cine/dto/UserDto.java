package com.electiva.cine.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;

    private String nick;

    private String pass;

    private String name;

    private String lastName;

    private RoleDto role;
}
