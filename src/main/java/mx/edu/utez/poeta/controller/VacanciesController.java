package mx.edu.utez.poeta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.utez.poeta.entity.Vacancies;
import mx.edu.utez.poeta.service.VacanciesService;

@RestController
@RequestMapping(value = "/vacancies")
public class VacanciesController {
    @Autowired
    private VacanciesService vacanciesService;

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public List<Vacancies> findAllVacancies() {
        return vacanciesService.findAllVacancies();
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    public Vacancies findVacanciesById(@PathVariable("id") long id) {
        return vacanciesService.findVacancieById(id);
    }

    @RequestMapping(name = "/delete/{id}", method = {RequestMethod.GET})
    public boolean delete(@PathVariable("id") long id) {
        return vacanciesService.delete(id);
    }
}
