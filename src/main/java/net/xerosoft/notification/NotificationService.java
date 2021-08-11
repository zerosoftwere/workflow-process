package net.xerosoft.notification;

import java.util.Collections;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import net.xerosoft.notification.rest.EmailRequest;
import net.xerosoft.notification.rest.NotificationRestClient;
import net.xerosoft.notification.rest.SmsRequest;

@ApplicationScoped
public class NotificationService {

    private static final int UNIT_LENGTH = 160;

    @Inject
    @RestClient
    NotificationRestClient notificationClient;

    public void sendEmail(UUID productId, String organisation, Email request) {
        EmailRequest email = new EmailRequest();
        email.from = request.from;
        email.html = true;
        email.message = request.message;
        email.recipients = request.to;
        email.replyTo = request.replyTo;
        email.subject = request.subject;

        notificationClient.sendEmail(productId, organisation, email);
    }

    public void sendSms(Sms request) {
        SmsRequest sms = new SmsRequest();
        sms.bulk = false;
        sms.message = request.message;
        sms.organisation = request.organisation;
        sms.units = request.message.length() / UNIT_LENGTH + 1;
        sms.sender = request.sender;
        sms.recipients = Collections.singleton(request.recipient);
        sms.triggeredBy = "Safapply";
        
        notificationClient.sendSms(sms);
    }
}
