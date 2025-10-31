package com.social.authservice.services;



import com.social.authservice.exceptions.InvalidTokenException;
import com.social.authservice.exceptions.PasswordMismatchException;
import com.social.authservice.exceptions.UserAlreadyExistException;
import com.social.authservice.exceptions.UserNotSignedException;
import com.social.authservice.models.Token;
import com.social.authservice.models.User;
import com.social.authservice.repositories.TokenRepository;
import com.social.authservice.repositories.UserRepository;
import org.antlr.v4.runtime.misc.Pair;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenRepository tokenRepository;

    public AuthServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
    }

    public User signup(String name, String email,
                       String password,String phoneNumber) {

        Optional<User> userOptional = userRepository.findByEmailEquals(email);

        if(userOptional.isPresent()){
            throw new UserAlreadyExistException("Please try login directly !!!");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setName(name);
        user.setPhoneNumber(phoneNumber);

        return userRepository.save(user);
    }

    public Token login(
            String email,
            String password
    ) {
        Optional<User> userOptional = userRepository.findByEmailEquals(email);

        if(userOptional.isEmpty()){
            throw new UserNotSignedException("Please try to signup and then login !!!");
        }

        String storedPassword = userOptional.get().getPassword();

        if(!bCryptPasswordEncoder.matches(password,storedPassword)){
            throw new PasswordMismatchException("please type correct password.");
        }

        // ToDo : Generate JWT
        Token token = new Token();
        token.setUser(userOptional.get());
        token.setValue(RandomStringUtils.randomAlphanumeric(128));

        Calendar calendar =Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,30);
        Date dateAfter30Days = calendar.getTime();

        token.setExpiresAt(dateAfter30Days);

        return tokenRepository.save(token);

    }

    @Override
    public User validateToken(String tokenValue) {
        Optional<Token> optionalToken = tokenRepository.
                findByValueAndExpiresAtAfter(tokenValue,new Date());

        if(optionalToken.isEmpty()){
            // The token is not valid or expired
            return null;
        }
        return optionalToken.get().getUser();
    }
}
