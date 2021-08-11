package net.xerosoft.credentials;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.scheduler.Scheduled;
import net.xerosoft.credentials.rest.TokenRequest;
import net.xerosoft.credentials.rest.TokenResponse;
import net.xerosoft.credentials.rest.TokenService;

@ApplicationScoped
public class TokenManager {
    private final Logger log = LoggerFactory.getLogger(TokenManager.class);

    @Inject
    @RestClient
    TokenService tokenService;

    @ConfigProperty(name = "auth.client.id")
    String clientId;

    @ConfigProperty(name = "auth.client.secret")
    String clientSecret;

    @ConfigProperty(name = "auth.client.grant-type")
    String grantType;

    private String accessToken;

    public String getToken() {
        if (accessToken == null)
            requestToken();

        return accessToken;
    }

    public String getTokenAsBearer() {
        return "Bearer " + getToken();
    }

    private void requestToken() {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.clientId = clientId;
        tokenRequest.clientSecret = clientSecret;
        tokenRequest.grantType = grantType;

        try {
            TokenResponse response = tokenService.request(tokenRequest);
            accessToken = response.accessToken;
        } catch (Exception ex) {
            log.warn("Fail to retrieve client token", ex);
        }
    }

    @Scheduled(every="20m")     
    void refreshToken() {
        this.requestToken(); 
    }
}
