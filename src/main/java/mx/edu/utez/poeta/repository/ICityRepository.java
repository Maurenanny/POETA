package mx.edu.utez.poeta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.poeta.entity.City;

public interface ICityRepository extends JpaRepository<City, Long> {
    
}
