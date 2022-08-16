package mx.edu.utez.poeta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.edu.utez.poeta.entity.UserConnection;

@Repository
public interface IUserConnectionRepository extends JpaRepository<UserConnection, Long> {
    UserConnection findByUser_Username(String username);
}
