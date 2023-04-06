package com.jylove.mpc.mpc.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Transactional
@Service
public class SendEmailService {

    public  void sendMail(String email, String temppassword) {
        String host = "smtp.naver.com";
        String user = "yjuny8313@naver.com";
        String password = "sibar8313@";

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(user,password);
            }
        });

        try {
            // 자바자체에서 지원하는 글레스
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            // 수신자 이메일
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            // 메일 제목
            message.setSubject("임시비밀번호 발급");
            // 메일 내용
            message.setText("임시비밀번호 발송해 드립니다." +temppassword);

            Transport.send(message);
            System.out.println("Success Message Send");

        }catch (MessagingException e){
            e.printStackTrace();
        }
    }

}
