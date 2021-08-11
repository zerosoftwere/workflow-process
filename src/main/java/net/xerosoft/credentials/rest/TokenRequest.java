package net.xerosoft.credentials.rest;

import javax.ws.rs.FormParam;

public class TokenRequest {
    @FormParam("client_id")
    public String clientId;
    
    @FormParam("client_secret")
    public String clientSecret;

    @FormParam("grant_type")
    public String grantType;
}
