package mx.edu.utez.poeta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.edu.utez.poeta.entity.City;

public interface ICityRepository extends JpaRepository<City, Long> {

    @Query(value = "SELECT * FROM city c WHERE c.state = :id", nativeQuery = true)
    List<City> findAllByStateId(@Param("id") long id);
    
}
