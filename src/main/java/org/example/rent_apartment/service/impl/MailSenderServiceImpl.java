package org.example.rent_apartment.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import lombok.RequiredArgsConstructor;
import org.example.rent_apartment.service.MailSenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MailSenderServiceImpl implements MailSenderService {

    private final JavaMailSender javaMailSender;


    @Value("${spring.mail.username}")
    private String userName;

    @Override
    public void sendEmail(String massage, String subject, String emailAddress) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(userName);
        simpleMailMessage.setTo(emailAddress);



        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(massage);

        try {
            javaMailSender.send(simpleMailMessage);
        } catch (MailException ex) {
            ((JavaMailSenderImpl) javaMailSender).getJavaMailProperties().setProperty("mail.smtp.quitwait", "true");
            Session session = ((JavaMailSenderImpl) javaMailSender).getSession();
            Transport transport;
            try {
                transport = session.getTransport();
                // Закрываем соединение
                if (transport != null) {
                    transport.close();
                }
            } catch (MessagingException ignored) {

            }
        }
    }
}
