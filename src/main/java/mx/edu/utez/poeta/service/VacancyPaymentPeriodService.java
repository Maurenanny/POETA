package mx.edu.utez.poeta.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.poeta.entity.VacancyPaymentPeriod;
import mx.edu.utez.poeta.repository.IVacancyPaymentPeriodRepository;

@Service
public class VacancyPaymentPeriodService {

    @Autowired
    private IVacancyPaymentPeriodRepository vacancyPaymentPeriodRepository;

    public List<VacancyPaymentPeriod> listAll() {
        return vacancyPaymentPeriodRepository.findAll();
    }
    
}
