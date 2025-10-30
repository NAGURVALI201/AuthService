package com.social.authservice.services;

import com.social.authservice.models.Token;
import com.social.authservice.models.User;
import org.antlr.v4.runtime.misc.Pair;

public interface AuthService {
    User signup(
            String name,
            String email,
            String password,
            String phoneNumber
    );
    Token login(
            String email,
            String password
    );
}
