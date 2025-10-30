package com.social.authservice.repositories;

import com.social.authservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {

    // select * from token where value = ? and expiresAt > now()
    Optional<Token> findByValueAndExpiresAtAfter(String tokenValue, Date currentDate);
}
