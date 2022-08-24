package mx.edu.utez.poeta.service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import mx.edu.utez.poeta.entity.PostulantProcess;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private Configuration configuration;

    public void sendEmail(PostulantProcess process, int type) throws MessagingException, IOException, TemplateException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        switch (type) {
            case 1: //hired
            helper.setSubject("¡Buenas Noticias!");
            break;
            case 2: //rejected
            helper.setSubject("Lo sentimos mucho...");
            break;
            case 3: //register
            helper.setSubject("¡Todo listo!");
            break;
            case 4:
            helper.setSubject("¡Prepárate!");
        }
        helper.setTo(process.getPostulant().getUsername());
        String emailContent = getEmailContent(process, type);
        helper.setText(emailContent, true);
        emailSender.send(mimeMessage);
    }

    String getEmailContent(PostulantProcess process, int type) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("process", process);
        switch (type) {
            case 1: //hired
            configuration.getTemplate("hired.ftlh").process(model, stringWriter);
            break;
            case 2: //rejected
            configuration.getTemplate("rejected.ftlh").process(model, stringWriter);
            break;
            case 3: //register
            configuration.getTemplate("register.ftlh").process(model, stringWriter);
            break;
            case 4: //cv
            configuration.getTemplate("cv.ftlh").process(model, stringWriter);
        }
        return stringWriter.getBuffer().toString();
    }
}
