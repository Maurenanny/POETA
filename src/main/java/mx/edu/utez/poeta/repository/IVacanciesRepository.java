package mx.edu.utez.poeta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.edu.utez.poeta.entity.Vacancies;

public interface IVacanciesRepository extends JpaRepository<Vacancies, Long> {

    @Query(value = "SELECT * FROM vacancies v WHERE v.recruiter_id = :id", nativeQuery = true)
    List<Vacancies> findAllRecruiterVacancies(@Param("id") long id);

    @Query(value = "SELECT * FROM vacancies v WHERE v.status = 1", nativeQuery = true)
    List<Vacancies> findAllActiveVacancies();

    @Query(value = "SELECT * FROM vacancies v WHERE v.id NOT IN (SELECT p.vacant_id FROM process p WHERE p.postulant_id = :id) AND v.status = 1", nativeQuery = true)
    List<Vacancies> findAllActiveNotRegisteredByPostulantVacancies(@Param("id") long id);

    @Modifying
    @Query(value = "UPDATE vacancies v SET v.status = 0 WHERE NOW() >= v.date_end", nativeQuery = true)
    void caducateVacancies();
    
}
