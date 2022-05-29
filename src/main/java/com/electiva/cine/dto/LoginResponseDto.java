package com.electiva.cine.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private String jwt;
    public LoginResponseDto(String jwt){
        this.jwt = jwt;
    }
}
