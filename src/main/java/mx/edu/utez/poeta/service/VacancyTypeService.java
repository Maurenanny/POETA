package mx.edu.utez.poeta.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.poeta.entity.VacancyType;
import mx.edu.utez.poeta.repository.IVacancyTypeRepository;

@Service
public class VacancyTypeService {
    @Autowired
    private IVacancyTypeRepository vacancyTypeRepository;

    public List<VacancyType> findAll() {
        return vacancyTypeRepository.findAll();
    }
}
