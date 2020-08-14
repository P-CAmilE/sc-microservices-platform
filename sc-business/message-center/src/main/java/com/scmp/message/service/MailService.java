package com.scmp.message.service;

import com.scmp.domain.DMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

/**
 * @author Coconut Tree
 * 暂时只测试 普通邮件
 * mime邮件 需公文也带附件, 感觉没必要
 */
@Service
@Slf4j
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * 根据 rocketmq 收到的信息 发送邮件
     * @param dMessage
     */
    public void sendEmailOnMessage(DMessage dMessage) {
        String from = "1442803701@qq.com";
        String greet = dMessage.getUsername() + ", 你好!";
        String subject = "[" + dMessage.getTitle() + "] " + greet;
        sendSimpleMailMessage(from, dMessage.getEmail(), subject, dMessage.getContent());
    }

    /**
     * 发送 普通邮件
     * @param from
     * @param to
     * @param subject
     * @param text
     */
    public void sendSimpleMailMessage(String from, String to, String subject, String text) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        try {
            mailSender.send(simpleMailMessage);
            log.warn("message-center|sendMailOk|from: {} ==> to: {}", from, to);
        } catch (Exception e) {
            log.error("message-center|errorSendMail|cause: {}", e.toString());
        }
    }

    /**
     * 发送 HTML 邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     */
    public String sendMimeHtmlMessage(String from, String to, String subject, String content) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // 可能出现异常，添加至函数签名
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            // true 表示发送 html 内容
            helper.setText(content, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.info("发送 HTML 邮件 Exception: {}", e.toString());
            e.printStackTrace();
            return "发送 HTML 邮件失败";
        }
        return "发送 HTML 邮件成功";
    }

    /**
     * 发送 带附件 的邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     */
    public String sendMimeAttachMessage(String from, String to, String subject, String content, String filePath) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // 可能出现异常，添加至函数签名
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            // true 表示发送 html 内容
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = file.getFilename();
            helper.addAttachment(fileName, file);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.info("发送 带附件 的邮件 Exception: {}", e.toString());
            e.printStackTrace();
            return "发送 带附件 的邮件失败";
        }
        return "发送 带附件 的邮件成功";
    }

    /**
     * 发送带多个附件的邮件
     *
     * @param to       收件人
     * @param subject  主题
     * @param content  内容
     * @param rscIdMap 需要替换的静态文件
     */
    public String sendMimeMultiAttachMessage(String from, String to, String subject, String content, Map<String, String> rscIdMap) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            for (Map.Entry<String, String> entry : rscIdMap.entrySet()) {
                FileSystemResource file = new FileSystemResource(new File(entry.getValue()));
                helper.addInline(entry.getKey(), file);
            }
            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("发送带多个附件的邮件 Exception: {}", e.toString());
            e.printStackTrace();
            return "发送带多个附件的邮件失败";
        }
        return "发送带多个附件的邮件成功";
    }

}
