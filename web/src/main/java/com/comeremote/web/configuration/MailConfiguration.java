package com.comeremote.web.configuration;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

//@Configuration
public class MailConfiguration {
//
//    @Value("${spring.mail.host}")
//    private String host;
//    @Value("${spring.mail.port}")
//    private Integer port;
//    @Value("${spring.mail.username}")
//    private String userName;
//    @Value("${spring.mail.password}")
//    private String password;
//    @Value("${spring.mail.protocol}")
//    private String protocol;
//    @Value("${mail.debug}")
//    private String debug;
//
//    @Bean
//    public JavaMailSender javaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//
//        mailSender.setHost(host);
//        mailSender.setPort(port);
//        mailSender.setUsername(userName);
//        mailSender.setPassword(password);
//
//        Properties properties = mailSender.getJavaMailProperties();
//
//        properties.setProperty("spring.mail.protocol", protocol);
//        properties.put("mail.smtps.host", host);
//        properties.put("mail.smtps.auth", "true");
//        properties.setProperty("mail.debug", debug);
//
////        mail.smtps.host
////        mail.smtps.auth
//        return mailSender;
//    }
}
