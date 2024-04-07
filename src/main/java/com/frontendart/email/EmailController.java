package com.frontendart.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmailController {

    @Autowired
    private SimpleEmailService simpleEmailService;

    @Autowired
    private ThymeleafEmailService thymeleafEmailService;

    @GetMapping("/emailForm")
    public String showEmailForm(Model model) {
        Email email = new Email();
        model.addAttribute("email", email);
        return "email-form";
    }

    @PostMapping("/sendEmail")
    public String sendEmail(@ModelAttribute Email email,
                            @RequestParam String action) {

        String emailSubject = email.getEmailSubject();
        String emailText = email.getEmailText();
        String receiverEmail = email.getReceiverEmail();

        try {
            if ("sendSimple".equals(action)) {

                simpleEmailService.sendEmail("noreply@kiscica.ai", receiverEmail, emailSubject, emailText);

            } else if ("sendThymeleaf".equals(action)) {

                    ModelMap model = new ModelMap();
                    model.addAttribute("emailText", emailText);
                    model.addAttribute("receiverEmail", receiverEmail);
                    thymeleafEmailService.sendEmail(receiverEmail, emailSubject, model);

            }

        } catch (Exception e) {
            return "email-error";
        }
        return "email-success";
    }
}
