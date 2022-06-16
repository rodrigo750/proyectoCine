package com.electiva.cine.api;


import com.electiva.cine.dto.LoginRequestDto;
import com.electiva.cine.dto.LoginResponseDto;
import com.electiva.cine.dto.ServiceResponseDto;
import com.electiva.cine.dto.UserDto;
import com.electiva.cine.security.jwt.JwtUtil;
import com.electiva.cine.services.MyUserDetailsService;
import com.electiva.cine.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



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
        }catch(Exception be){
            throw new Exception("Bad username or password",be);
        }
        String jwt = jwtUtil.generateToken(myUserDetailsService.loadUserByUsername(loginRequestDto.getUsername()));
        return ResponseEntity.ok(new LoginResponseDto(jwt));
    }

    @PostMapping("/admin/users")
    public ResponseEntity<?> createAdminUser(@RequestBody UserDto userDto){
        ServiceResponseDto serviceResponseDto = userService.createAdminUser(userDto);
        if(serviceResponseDto.getCode().equals("OK")){
            return ResponseEntity.status(HttpStatus.OK).body(serviceResponseDto);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(serviceResponseDto);
        }
    }
}
