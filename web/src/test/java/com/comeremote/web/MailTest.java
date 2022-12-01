package com.comeremote.web;

import javax.mail.MessagingException;
import com.comeremote.web.service.SimpleMailSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailTest {

    @Autowired
    private SimpleMailSender simpleMailSender;

    @Test
    public void sendMailTest() throws MessagingException {

        simpleMailSender.send("mail@mail.com", "Test mail", "Hello from Java");
    }
}
