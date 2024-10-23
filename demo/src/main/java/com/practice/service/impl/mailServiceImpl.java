package com.practice.service.impl;

import com.practice.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;


@Service
public class mailServiceImpl implements IMailService {

    @Autowired
    private JavaMailSender javaMailSender;

    private String temporaryStorage;
    @Override
    public boolean sendMail(String email) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false);
            helper.setFrom("2629016097@qq.com", "knwwcqfyueypdiag");
            helper.setTo(email);
            System.out.println(email);
            String random = "" + Math.random();
            String substring = random.substring(random.length() - 4);
            helper.setSubject(substring);
            helper.setText(substring, true);
            javaMailSender.send(message);
            temporaryStorage = substring;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getTemporaryStorage() {
        return temporaryStorage;
    }
}
