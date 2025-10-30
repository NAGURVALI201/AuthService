package com.social.authservice.dtos;

import com.social.authservice.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TokenDto {
    private String token;
    private String email;
    private List<Role> roles;
}
