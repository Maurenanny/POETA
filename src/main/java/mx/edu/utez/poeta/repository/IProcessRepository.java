package mx.edu.utez.poeta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.edu.utez.poeta.entity.Process;

public interface IProcessRepository extends JpaRepository<Process, Long> {

    @Query(value = "SELECT * FROM process p WHERE p.postulant_id = :id", nativeQuery = true) //Si se necesita, agregar un order by status para mostrar primero los activos y luego los inactivos
    List<Process> findAllProcessesByUserId(@Param("id") long id);

    @Query(value = "SELECT * FROM process p WHERE p.postulant_id = :id AND p.favorite = 1", nativeQuery = true)
    List<Process> findAllFavoriteProcessesByUserId(@Param("id") long id);
    
}
