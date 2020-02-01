package com.saloon.beauty.services.emailService;

import com.saloon.beauty.repository.entity.Slot;
import com.saloon.beauty.services.ServiceFactory;
import com.saloon.beauty.services.SlotService;
import com.saloon.beauty.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Contains util methods for service of sending email
 */
public class EmailSenderUtils {

    private static final Logger LOG = LogManager.getLogger(EmailSenderUtils.class);

    private static EmailSenderUtils instance = new EmailSenderUtils();

    private static UserService userService;
    private static SlotService slotService;

    static {
        setServices();
    }

    public synchronized void sendEmail() {
        Properties properties = getEmailSetting();

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getProperty("username")
                        , properties.getProperty("password"));
            }
        });

        List<String> emailList = getUsersEmail();

        try {
            for (String email: emailList) {
                Transport.send(prepareMessage(session, properties, email));
            }
        } catch (MessagingException e) {
            String errorText = String.format("Can't send letter by combination email: %s and password: %s." +
                    "\nCause: %s.", properties.get("username"), properties.get("password"), e.getMessage());
            LOG.error(errorText, e);
            throw new EmailSenderException(errorText, e);
        }

    }

    /**
     * Create an email message for sending
     * @param session - session with login and password for email
     * @param properties - properties with configuration for crating message
     * @param email - target email to sending message
     * @return a {@code Message} object to future sending
     */
    private static synchronized Message prepareMessage(Session session, Properties properties, String email) {

        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(properties.getProperty("username")));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(properties.getProperty("message.header"));

            String msg = properties.getProperty("message.text");

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);
        } catch (MessagingException e) {
            String errorText = String.format("Can't prepare message. Cause: %s.", e.getMessage());
            LOG.error(errorText, e);
            throw new EmailSenderException(errorText, e);
        }
        return message;
    }


    /**
     * Read configure from properties file
     * @return a {@code Properties} object with setting for sender from configure file
     */
    private static Properties getEmailSetting() {

        Properties properties = new Properties();

        try (InputStream inputStream = EmailSenderUtils.class.getClassLoader().getResourceAsStream("emailConfig.properties")) {

            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new FileNotFoundException();
            }

        } catch (IOException e) {
            LOG.error("Can't read emailConfig.properties file.", e);

        }
        return properties;
    }

    public static EmailSenderUtils getInstance() {
        return instance;
    }

    private EmailSenderUtils() {
    }

    private static void setServices() {
        userService = (UserService) new ServiceFactory().getService("com.saloon.beauty.services.UserService");
        slotService = (SlotService) new ServiceFactory().getService("com.saloon.beauty.services.SlotService");
    }

    /**
     * Search slots in which status of feedback request set as false
     * @return a {@code List} of email of user, which is not received request to write feedback yet
     */
    private static List<String> getUsersEmail() {

        List<String> emailList = new ArrayList<>();

        List<Slot> slotList = slotService.getSlotByStatusFeedbackRequest(false);

        for (Slot slot : slotList) {
            if (slot.getDate().isBefore(LocalDate.now())) {
                if (!slot.isFeedbackRequest()) {
                    emailList.add(userService.getUserById(slot.getUser()).get().getEmail());
                    slotService.setFeedbackRequestStatusTrue(slot.getId());
                }
            } else if (slot.getDate().equals(LocalDate.now()) &&
                    slot.getEndTime().isAfter(LocalTime.now())) {
                if (!slot.isFeedbackRequest()) {
                    emailList.add(userService.getUserById(slot.getUser()).get().getEmail());
                    slotService.setFeedbackRequestStatusTrue(slot.getId());
                }

            }
        }

        return emailList;
    }

}
