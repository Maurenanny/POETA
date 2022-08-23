package mx.edu.utez.poeta.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.poeta.entity.Vacancies;
import mx.edu.utez.poeta.repository.IVacanciesRepository;

@Service
@Transactional
public class VacanciesService {
    
    @Autowired
    private IVacanciesRepository vacanciesRepository;

    @Transactional()
    public List<Vacancies> findAllVacancies() {
        vacanciesRepository.caducateVacancies();
        return vacanciesRepository.findAllActiveVacancies();
    }

    @Transactional(readOnly = true)
    public Vacancies findVacancieById(long id) {
        return vacanciesRepository.findById(id).get();
    }

    @Transactional()
    public List<Vacancies> findAllActiveNotRegisteredByPostulantVacancies(long id) {
        vacanciesRepository.caducateVacancies();
        return vacanciesRepository.findAllActiveNotRegisteredByPostulantVacancies(id);
    }

    public boolean save(Vacancies obj) {
        boolean flag = false;
        Vacancies tmp = vacanciesRepository.save(obj);
        if (tmp != null) {
            flag = true;
        }
        return flag;
    }

    public boolean delete(long id) {
        boolean flag = false;
        Vacancies tmp = findVacancieById(id);
        if (tmp != null) {
            vacanciesRepository.delete(tmp);
            flag = true;
        }
        return flag;
    }

    @Transactional(readOnly = true)
    public List<Vacancies> findAllRecruiterVacancies(long id) {
        return vacanciesRepository.findAllRecruiterVacancies(id);
    }

}
