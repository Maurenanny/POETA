package mx.edu.utez.poeta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.utez.poeta.entity.Email;
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

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public List<Process> findAllProcesses() {
        return processService.findAllProcesses();
    }

    @RequestMapping(value = "/mail/test", method = {RequestMethod.GET})
    public boolean sentTestMail() {
        try {
            //emailService.test("ulisesislas@utez.edu.mx", "prueba", "<h1>Hola</h1>");
            Email test = new Email();
            test.setTo("ulisesislas@utez.edu.mx");
            emailService.test(test);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    public Process findProcessById(@PathVariable("id") long id) {
        return processService.findProcessById(id);
    }

    @RequestMapping(name = "/save", method = {RequestMethod.POST})
    public boolean save(@RequestBody Process obj) {
        return processService.save(obj);
    }

    @RequestMapping(name = "/delete/{id}", method = {RequestMethod.GET})
    public boolean delete(@PathVariable("id") long id) {
        return processService.delete(id);
    }

}
