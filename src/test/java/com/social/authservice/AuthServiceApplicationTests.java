package com.social.authservice;

import com.social.authservice.security.repositories.JpaRegisteredClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.UUID;

@SpringBootTest
class AuthServiceApplicationTests {

    private final JpaRegisteredClientRepository registeredClientRepository;

    @Autowired
    AuthServiceApplicationTests(JpaRegisteredClientRepository registeredClientRepository) {
        this.registeredClientRepository = registeredClientRepository;
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void testCase(){
        RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("oidc-client")
				.clientSecret("$2a$12$KUmrSPhYoaUdU5mfn46sheX/j4X490mqyE/35y3uN2DG/k/Tdbzwi")
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.redirectUri("https://oauth.pstmn.io/v1/callback")
				.postLogoutRedirectUri("http://127.0.0.1:8080/")
				.scope("ADMIN")
				.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
				.build();

        registeredClientRepository.save(oidcClient);
    }
}
