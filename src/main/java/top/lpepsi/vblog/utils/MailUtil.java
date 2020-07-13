package top.lpepsi.vblog.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import top.lpepsi.vblog.service.user.impl.UserServiceImpl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @program: v-blog
 * @description: 邮件工具类
 * @author: 林北
 * @create: 2020-02-17 17:21
 **/
@Component
public class MailUtil {
    private static final String FROM = "pepsic@qq.com";

    private static final Logger LOGGER = LoggerFactory.getLogger(MailUtil.class);

    @Autowired
    private JavaMailSenderImpl mailSender;

    public void sendMail(String to, String message) throws MessagingException {
//            String address = " http://localhost:8777/statusCode?code="+message;
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setSubject("VBLOG-验证码");
            mimeMessageHelper.setFrom(FROM);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setText("验证码为： "+message+"， 五分钟内有效");
            mailSender.send(mimeMessage);
    }

    @Bean
    private void setMailConfig(){
        Properties javaMailProperties = new Properties();
        javaMailProperties.setProperty("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        javaMailProperties.setProperty("mail.smtp.socketFactory.port", "465");
        javaMailProperties.setProperty("mail.smtp.port", "465");
        mailSender.setJavaMailProperties(javaMailProperties);
    }
}
