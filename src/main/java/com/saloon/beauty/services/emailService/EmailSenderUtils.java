package com.saloon.beauty.services.emailService;

import com.saloon.beauty.repository.entity.Slot;
import com.saloon.beauty.services.ServiceFactory;
import com.saloon.beauty.services.SlotService;
import com.saloon.beauty.services.UserService;

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

public class EmailSenderUtils {

    private static EmailSenderUtils instance = new EmailSenderUtils();

    private UserService userService;
    private SlotService slotService;


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
            e.printStackTrace();
        }

    }

    private synchronized Message prepareMessage(Session session, Properties properties, String email) {

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
            e.printStackTrace();
        }
        return message;
    }

    private Properties getEmailSetting() {

        Properties properties = new Properties();

        try (InputStream inputStream = EmailSenderUtils.class.getClassLoader().getResourceAsStream("emailConfig.properties")) {

            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new FileNotFoundException();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static EmailSenderUtils getInstance() {
        return instance;
    }

    private EmailSenderUtils() {
    }

    private void setServices() {
        userService = (UserService) new ServiceFactory().getService("com.saloon.beauty.services.UserService");
        slotService = (SlotService) new ServiceFactory().getService("com.saloon.beauty.services.SlotService");
    }

    private List<String> getUsersEmail() {
        setServices();

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
