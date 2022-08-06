package mx.edu.utez.poeta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.edu.utez.poeta.entity.User;

public interface IUserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    
}
