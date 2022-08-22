package mx.edu.utez.poeta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.edu.utez.poeta.entity.Vacancies;

public interface IVacanciesRepository extends JpaRepository<Vacancies, Long> {

    @Query(value = "SELECT * FROM vacancies v WHERE v.recruiter_id = :id", nativeQuery = true)
    List<Vacancies> findAllRecruiterVacancies(@Param("id") long id);
    
}
