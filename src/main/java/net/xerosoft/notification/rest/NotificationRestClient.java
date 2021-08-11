package net.xerosoft.notification.rest;

import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

@Path("/")
@RegisterClientHeaders(RequestHeaderFactory.class)
@RegisterRestClient(configKey = "notification-api")
public interface NotificationRestClient {
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("platform/{productId}/organisation/{organisation}/email")
    void sendEmail(
        @PathParam("productId") UUID productId,
        @PathParam("organisation") String organisation,
        @MultipartForm EmailRequest email);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("sms")
    void sendSms(SmsRequest sms);
}
