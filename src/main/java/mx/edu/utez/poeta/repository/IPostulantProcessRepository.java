package mx.edu.utez.poeta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.edu.utez.poeta.entity.PostulantProcess;

public interface IPostulantProcessRepository extends JpaRepository<PostulantProcess, Long> {

    @Query(value = "SELECT * FROM process p WHERE p.postulant_id = :id", nativeQuery = true) //Si se necesita, agregar un order by status para mostrar primero los activos y luego los inactivos
    List<PostulantProcess> findAllProcessesByUserId(@Param("id") long id);

    @Query(value = "SELECT * FROM process p WHERE p.postulant_id = :id AND p.favorite = 1", nativeQuery = true)
    List<PostulantProcess> findAllFavoriteProcessesByUserId(@Param("id") long id);

    @Query(value = "SELECT * FROM process p WHERE p.postulant_id = :id AND p.status = 0", nativeQuery = true)
    List<PostulantProcess> findAllRejectedProcessesByUserId(@Param("id") long id);

    @Query(value = "SELECT * FROM process p WHERE p.postulant_id = :id AND p.status = 5", nativeQuery = true)
    List<PostulantProcess> findAllAcceptedProcessesByUserId(@Param("id") long id);

    @Query(value = "SELECT * FROM process p WHERE p.vacant_id = :id AND (p.status = 1 OR p.status = 2)", nativeQuery = true)
    List<PostulantProcess> findAllProcessFromVacancyStageOne(@Param("id") long id);
    
    @Query(value = "SELECT * FROM process p WHERE p.vacant_id = :id AND p.status = 3", nativeQuery = true)
    List<PostulantProcess> findAllProcessFromVacancyStageTwo(@Param("id") long id);

    @Query(value = "SELECT * FROM process p WHERE p.vacant_id = :id AND p.status = 4", nativeQuery = true)
    List<PostulantProcess> findAllProcessFromVacancyStageThree(@Param("id") long id);

    @Query(value = "SELECT * FROM process p WHERE p.vacant_id = :id AND p.status = 5", nativeQuery = true)
    List<PostulantProcess> findAcceptedPostulantFromVacancy(@Param("id") long id);

    @Query(value = "SELECT * FROM process p WHERE p.vacant_id = :vacancyId AND p.postulant_id <> :postulantId AND p.status <> 0", nativeQuery = true)
    List<PostulantProcess> findAllPostulantsForRejection(@Param("vacancyId") long vacancyId, @Param("postulantId") long postulantId);

    @Modifying
    @Query(value = "UPDATE process p SET p.status = 0 WHERE p.vacant_id = :vacancyId AND p.postulant_id <> :postulantId AND p.status <> 0", nativeQuery = true)
    void CloseProcess(@Param("vacancyId") long vacancyId, @Param("postulantId") long postulantId);
    
}
