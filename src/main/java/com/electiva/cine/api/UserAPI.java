package com.electiva.cine.api;


import com.electiva.cine.dto.LoginRequestDto;
import com.electiva.cine.dto.LoginResponseDto;
import com.electiva.cine.entity.UserEntity;
import com.electiva.cine.security.jwt.JwtUtil;
import com.electiva.cine.services.MyUserDetailsService;
import com.electiva.cine.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserAPI {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequestDto loginRequestDto) throws Exception{
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword()));
        }catch(BadCredentialsException be){
            throw new Exception("Bad username or password",be);
        }
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(loginRequestDto.getUsername());
        String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponseDto(jwt));
    }

    @GetMapping("/users")
    public List<UserEntity> getUsers(){
        return userService.findAll();
    }
}
