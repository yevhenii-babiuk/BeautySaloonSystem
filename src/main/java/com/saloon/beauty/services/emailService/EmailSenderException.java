package com.saloon.beauty.services.emailService;

/**
 * {@code RuntimeException} for exceptional situations
 * during sending email methods working
 */
public class EmailSenderException extends RuntimeException {

    public EmailSenderException(String message) {
        super(message);
    }

    public EmailSenderException(String message, Throwable cause) {
        super(message, cause);
    }
}
