package mx.edu.utez.poeta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.utez.poeta.entity.GeneralTemplateResponse;
import mx.edu.utez.poeta.service.VacancyPaymentPeriodService;

@RestController
@RequestMapping(value = ("/period"))
public class VacancyPaymentPeriodController {
    @Autowired
    private VacancyPaymentPeriodService paymentPeriodService;

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public GeneralTemplateResponse findAll() {
        return new GeneralTemplateResponse(paymentPeriodService.listAll());
    }
}
