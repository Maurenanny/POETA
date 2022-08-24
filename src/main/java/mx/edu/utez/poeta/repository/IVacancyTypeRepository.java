package mx.edu.utez.poeta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.poeta.entity.VacancyType;

public interface IVacancyTypeRepository extends JpaRepository<VacancyType, Long> {
    
}
