package xyz.litterboys.esblog.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import xyz.litterboys.esblog.service.EmailService;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    @Resource
    private JavaMailSender mailSender;

    private final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Override
    public void sendEmail(String to, String subject, String content) {

        MimeMessage message=mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            helper.setFrom("cserwen-notify<cserwen_notify@163.com>");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);
            mailSender.send(message);
            logger.info("邮件接收人"+to+"主题"+subject+"邮件发送成功");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
