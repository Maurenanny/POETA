package mx.edu.utez.poeta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.edu.utez.poeta.entity.PostulantCV;

public interface IPostulantCVRepository extends JpaRepository<PostulantCV, Long> {
    
    @Query(value = "SELECT * FROM postulant_cv cv WHERE cv.postulant_id = :id", nativeQuery = true)
    public PostulantCV findPostulantCVByUserId(@Param("id") long id);

}
