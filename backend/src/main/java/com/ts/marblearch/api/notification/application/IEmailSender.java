package com.ts.marblearch.api.notification.application;

import com.ts.marblearch.api.sharedKernel.domain.valuesObjects.Email;

import java.util.Map;

public interface IEmailSender {
    void sendHtmlEmail(Email to, String subject, String body);
    void sendTemplateEmail(Email to, String subject, String templateName, Map<String, Object> variables);
}
