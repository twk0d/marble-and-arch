package com.ts.marblearch.api.notification.infrastructure.services;

import com.ts.marblearch.api.notification.application.IEmailSender;
import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Email;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class JavaEmailSender implements IEmailSender {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    @Override
    public void sendHtmlEmail(Email to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@marble-and-arch.com");
            message.setTo(to.address());
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
            log.info("Simple email sent successfully to {}", to.address());
        } catch (Exception e) {
            log.error("Failed to send simple email to {}", to.address(), e);
        }
    }

    @Async
    @Override
    public void sendTemplateEmail(Email to, String subject, String templateName, Map<String, Object> variables) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

            Context context = new Context();
            context.setVariables(variables);
            String htmlContent = templateEngine.process(templateName, context);

            helper.setFrom("noreply@marble-and-arch.com");
            helper.setTo(to.address());
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);
            log.info("Template email ({}) sent successfully to {}", templateName, to.address());
        } catch (Exception e) {
            log.error("Failed to send template email to {}", to.address(), e);
        }
    }
}
