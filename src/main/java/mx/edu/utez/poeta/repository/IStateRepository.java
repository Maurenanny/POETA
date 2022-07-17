package mx.edu.utez.poeta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.poeta.entity.State;

public interface IStateRepository extends JpaRepository<State, Long> {
    
}
