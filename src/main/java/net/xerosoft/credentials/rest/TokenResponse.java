package net.xerosoft.credentials.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenResponse {
    @JsonProperty("access_token")
    public String accessToken;

    @JsonProperty("expires_in")
    public String expiresIn;

    @JsonProperty("token_type")
    public String tokenType;
}
