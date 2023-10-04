package com.api.tcc.services;

import com.api.tcc.enums.StatusEmail;
import com.api.tcc.models.EmailModel;
import com.api.tcc.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

    @Autowired
    EmailRepository repository;

    @Autowired
    JavaMailSender emailSender;

    public void sendEmail(EmailModel emailModel){
        emailModel.setOwnerRef("Eric");
        emailModel.setEmailFrom("ericmtfontes@gmail.com");
        emailModel.setSubject("Mensagem de aluguel");
        emailModel.setSendDateEmail(LocalDateTime.now());
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailModel.getEmailFrom());
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());
            emailSender.send(message);

            emailModel.setStatusEmail(StatusEmail.SENT);
        }catch(MailException e){
            emailModel.setStatusEmail(StatusEmail.ERROR);
        }finally {
            repository.save(emailModel);
        }

    }
}
