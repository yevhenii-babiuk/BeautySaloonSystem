package com.saloon.beauty.services.emailService;

public class EmailSenderService implements Runnable {
    @Override
    public void run() {
        EmailSenderUtils.getInstance().sendEmail();

    }
}
