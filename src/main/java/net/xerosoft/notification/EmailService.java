package net.xerosoft.notification;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class EmailService {
    @Inject
    NotificationService notificationService;

    public void sendMail(String to, String subject, String message) {

        Email email = new Email();
        email.from = "atbu@safapply.com";
        email.to = to;
        email.replyTo = "no-reply@safapply.com";
        email.message = message;
        email.subject = subject;

        notificationService.sendEmail(UUID.fromString("48299954-bd3d-11ea-b3de-0242ac130054"), "ICS", email);
    }
}
