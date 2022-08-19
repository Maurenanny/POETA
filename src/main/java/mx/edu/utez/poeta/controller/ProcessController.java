package mx.edu.utez.poeta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.utez.poeta.config.AuthCheckPermission;
import mx.edu.utez.poeta.entity.GeneralTemplateResponse;
import mx.edu.utez.poeta.entity.Process;
import mx.edu.utez.poeta.service.EmailService;
import mx.edu.utez.poeta.service.ProcessService;

@RestController
@RequestMapping(path = "/process")
public class ProcessController {

    @Autowired
    private ProcessService processService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthCheckPermission authCheckPermission;

    @RequestMapping(value = "/list", method = { RequestMethod.GET })
    public GeneralTemplateResponse findAllProcesses() {
        return new GeneralTemplateResponse(processService.findAllProcesses());
    }

    @RequestMapping(value = "/mail/{id}/{type}", method = { RequestMethod.GET })
    public void sendEmail(@RequestHeader HttpHeaders headers, @PathVariable("id") long id,
            @PathVariable("type") int type) {
        String token = String.valueOf(headers.get("authorization"));
        if (authCheckPermission.checkPermission(token, "candidato")
                || authCheckPermission.checkPermission(token, "reclutador")) {
            try {
                Process process = processService.findProcessById(id);
                emailService.sendEmail(process, type);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @RequestMapping(value = "/{id}", method = { RequestMethod.GET })
    public GeneralTemplateResponse findProcessById(@PathVariable("id") long id, @RequestHeader HttpHeaders headers) {
        String token = String.valueOf(headers.get("authorization"));
        if (authCheckPermission.checkPermission(token, "candidato")) {
            return new GeneralTemplateResponse(processService.findProcessById(id));
        }
        return new GeneralTemplateResponse();
    }

    @RequestMapping(name = "/save", method = { RequestMethod.POST })
    public GeneralTemplateResponse save(@RequestBody Process obj, @RequestHeader HttpHeaders headers) {
        String token = String.valueOf(headers.get("authorization"));
        if (authCheckPermission.checkPermission(token, "candidato")) {
            new GeneralTemplateResponse(processService.save(obj));
        } 
        return new GeneralTemplateResponse();
    }

    @RequestMapping(name = "/delete/{id}", method = { RequestMethod.GET })
    public GeneralTemplateResponse delete(@PathVariable("id") long id, @RequestHeader HttpHeaders headers) {
        String token = String.valueOf(headers.get("authorization"));
        if (authCheckPermission.checkPermission(token, "candidato")) {
            return new GeneralTemplateResponse(processService.delete(id));
        }
        return new GeneralTemplateResponse();
    }

}
