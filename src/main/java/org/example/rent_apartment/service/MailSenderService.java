package org.example.rent_apartment.service;

public interface MailSenderService {

    void sendEmail(String massage, String subject, String emailAddress);
}
