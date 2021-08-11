package net.xerosoft.notification.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MultivaluedMap;


import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;
import org.jboss.resteasy.specimpl.MultivaluedMapImpl;

import net.xerosoft.credentials.TokenManager;

@ApplicationScoped
public class RequestHeaderFactory implements ClientHeadersFactory {

    @Inject
    TokenManager tokenManager;

    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders,
            MultivaluedMap<String, String> clientOutgoingHeaders) {
        MultivaluedMap<String, String> headers = new MultivaluedMapImpl<>();

        headers.add("authorization", tokenManager.getTokenAsBearer());
        return headers;
    }
    
}
