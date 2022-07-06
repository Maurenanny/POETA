package mx.edu.utez.poeta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.poeta.entity.Roles;

public interface IRolesRepository extends JpaRepository<Roles, Long> {
    
}
