package com.example.userreg.util;

import com.example.userreg.constants.AppConstants;
import com.example.userreg.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;

@Component
public class EmailUtils {

    @Autowired
    private JavaMailSender mailSender;

    public boolean sendEmail(String to, String subject, String body) {
        boolean isSent = false;
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setText(body, true);
            mailSender.send(mimeMessageHelper.getMimeMessage());
            isSent = true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return isSent;
    }

    public String generateMailBody(User entity, String pwd) {
        StringBuffer sb = new StringBuffer("");
        String mailBody = "";
        try {
            FileReader fr = new FileReader(AppConstants.UNLOCK_ACC_EMAIL_BODY_FILE);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            mailBody = sb.toString();
            mailBody = mailBody.replace("{FNAME}", entity.getFname());
            mailBody = mailBody.replace("{LNAME}", entity.getLname());
            mailBody = mailBody.replace("{TEMP-PWD}", pwd);
            mailBody = mailBody.replace("{EMAIL}", entity.getEmail());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mailBody;
    }

    public String readForgotPwdEmailBody(User entity) {
        StringBuffer sb = new StringBuffer("");
        String mailBody = "";
        try {
            FileReader fr = new FileReader(AppConstants.RECOVER_PWD_EMAIL_BODY_FILE);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            mailBody = sb.toString();
            mailBody = mailBody.replace("{FNAME}", entity.getFname());
            mailBody = mailBody.replace("{LNAME}", entity.getLname());
            mailBody = mailBody.replace("{PWD}", PwdUtils.decrypt(entity.getUsrPwd()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mailBody;
    }
}
