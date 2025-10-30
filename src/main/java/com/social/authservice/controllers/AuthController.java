package com.social.authservice.controllers;


import com.social.authservice.dtos.LoginRequestDto;
import com.social.authservice.dtos.SignupRequestDto;
import com.social.authservice.dtos.UserDto;
import com.social.authservice.models.User;
import com.social.authservice.services.AuthService;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public UserDto signup(@RequestBody SignupRequestDto signupRequestDto){

        User user = authService.signup(
                signupRequestDto.getName(),
                signupRequestDto.getEmail(),
                signupRequestDto.getPassword(),
                signupRequestDto.getPhoneNumber()
        );

        return toUserDto(user);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto){
        Pair<User,String> userWithToken = authService.login(
                loginRequestDto.getEmail(),
                loginRequestDto.getPassword()
        );
        UserDto userDto = toUserDto(userWithToken.a);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    public UserDto toUserDto(User user){

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setRoles(user.getRoles());

        return userDto;
    }
}
