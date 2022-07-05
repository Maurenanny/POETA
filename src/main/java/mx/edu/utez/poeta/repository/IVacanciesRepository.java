package mx.edu.utez.poeta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.poeta.entity.Vacancies;

public interface IVacanciesRepository extends JpaRepository<Vacancies, Long> {
    
}
