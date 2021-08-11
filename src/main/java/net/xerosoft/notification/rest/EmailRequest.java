package net.xerosoft.notification.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class EmailRequest {
    @FormParam("recipients")
    @PartType(MediaType.TEXT_PLAIN)
    public String recipients;
    
    @FormParam("message")
    @PartType(MediaType.TEXT_PLAIN)
    public String message;

    @FormParam("subject")
    @PartType(MediaType.TEXT_PLAIN)
    public String subject;

    @FormParam("from")
    @PartType(MediaType.TEXT_PLAIN)
    public String from;

    @FormParam("replyTo")
    @PartType(MediaType.TEXT_PLAIN)
    public String replyTo;

    @FormParam("isHtml")
    @PartType(MediaType.TEXT_PLAIN)
    public boolean html;
}
