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
import mx.edu.utez.poeta.entity.Vacancies;
import mx.edu.utez.poeta.service.VacanciesService;

@RestController
@RequestMapping(value = "/vacancies")
public class VacanciesController {
    @Autowired
    private VacanciesService vacanciesService;
    @Autowired 
    AuthCheckPermission authCheckPermission;

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public GeneralTemplateResponse findAllVacancies() {
        return new GeneralTemplateResponse(vacanciesService.findAllVacancies());
    }

    @RequestMapping(value = "/list/{id}", method = {RequestMethod.GET})
    public GeneralTemplateResponse findAllActiveNotRegisteredByPostulantVacancies(@RequestHeader HttpHeaders headers, @PathVariable("id") long id) {
        String token = String.valueOf(headers.get("authorization"));
        if (authCheckPermission.checkPermission(token, "candidato")) {
            return new GeneralTemplateResponse(vacanciesService.findAllActiveNotRegisteredByPostulantVacancies(id));
        }
        return new GeneralTemplateResponse();
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    public GeneralTemplateResponse findVacanciesById(@PathVariable("id") long id) {
        return new GeneralTemplateResponse(vacanciesService.findVacancieById(id));
    }

    @RequestMapping(name = "/delete/{id}", method = {RequestMethod.GET})
    public boolean delete(@PathVariable("id") long id) {
        return vacanciesService.delete(id);
    }

    @RequestMapping(value = "/recruiter/{id}", method = {RequestMethod.GET})
    public GeneralTemplateResponse findRecruiterVacancies(@PathVariable("id") long id, @RequestHeader HttpHeaders headers) {
        String token = String.valueOf(headers.get("authorization"));
        if (authCheckPermission.checkPermission(token, "reclutador")) {
            if (authCheckPermission.isLoguedUser(token, id)) {
                return new GeneralTemplateResponse(vacanciesService.findAllRecruiterVacancies(id));
            } else {
                return new GeneralTemplateResponse("No puedes consultar las vacantes de otros reclutadores desde aqui");
            }
        }
        return new GeneralTemplateResponse();
    }

    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public GeneralTemplateResponse save(@RequestBody Vacancies obj, @RequestHeader HttpHeaders headers) {
        String token = String.valueOf(headers.get("authorization"));
        if (authCheckPermission.checkPermission(token, "reclutador")) {
            return new GeneralTemplateResponse(vacanciesService.save(obj));
        }
        return new GeneralTemplateResponse();
    }
}
