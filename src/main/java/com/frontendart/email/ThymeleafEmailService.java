package com.frontendart.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import javax.mail.internet.MimeMessage;

@Service
public class ThymeleafEmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendEmail(String to, String subject, ModelMap model) throws Exception {
        Context context = new Context();
        context.setVariables(model);

        String process = templateEngine.process("email-template", context);

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setText(process, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setFrom("noreply@kiscica.ai");
        emailSender.send(mimeMessage);
    }
}
