package com.comeremote.web.service;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleMailSender {

    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private Integer port;
    @Value("${spring.mail.username}")
    private String userName;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.protocol}")
    private String protocol;

    public void send(String emailTo, String subject, String content) {

        Properties props = new Properties();

        props.put("mail.transport.protocol", protocol);
        props.put("mail.smtps.host", host);
        props.put("mail.smtps.auth", "true");
        // props.put("mail.smtps.quitwait", "false");

        try {
            Session mailSession = Session.getDefaultInstance(props);
            mailSession.setDebug(true);
            Transport transport = mailSession.getTransport();

            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject(subject);
            message.setContent(content, "text/html");

            message.addRecipient(Message.RecipientType.TO,
                                 new InternetAddress(emailTo));

            transport.connect
                    (host, port, userName, password);

            transport.sendMessage(message,
                                  message.getRecipients(Message.RecipientType.TO));
            transport.close();
        } catch (MessagingException e) {
            log.error("Failed to send message to {}: {}", emailTo, e.getMessage());
            e.printStackTrace();
        }
    }
}
