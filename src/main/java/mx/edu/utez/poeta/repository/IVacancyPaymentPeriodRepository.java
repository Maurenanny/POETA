package mx.edu.utez.poeta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.poeta.entity.VacancyPaymentPeriod;

public interface IVacancyPaymentPeriodRepository extends JpaRepository<VacancyPaymentPeriod, Long> {
    
}
