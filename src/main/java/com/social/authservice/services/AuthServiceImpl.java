package com.social.authservice.services;



import com.social.authservice.exceptions.PasswordMismatchException;
import com.social.authservice.exceptions.UserAlreadyExistException;
import com.social.authservice.exceptions.UserNotSignedException;
import com.social.authservice.models.User;
import com.social.authservice.repositories.UserRepository;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;


    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signup(String name, String email,
                       String password,String phoneNumber) {

        Optional<User> userOptional = userRepository.findByEmailEquals(email);

        if(userOptional.isPresent()){
            throw new UserAlreadyExistException("Please try login directly !!!");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password); // Todo: Use Bcrypt here
        user.setName(name);
        user.setPhoneNumber(phoneNumber);

        return userRepository.save(user);
    }

    public Pair<User,String> login(
            String email,
            String password
    ) {
        Optional<User> userOptional = userRepository.findByEmailEquals(email);

        if(userOptional.isEmpty()){
            throw new UserNotSignedException("Please try to signup and then login !!!");
        }

        String storedPassword = userOptional.get().getPassword();

        if(!password.equals(storedPassword)){
            throw new PasswordMismatchException("please type correct password.");
        }

        // ToDo : Generate JWT
        return new Pair<>(userOptional.get(),"");

    }
}
