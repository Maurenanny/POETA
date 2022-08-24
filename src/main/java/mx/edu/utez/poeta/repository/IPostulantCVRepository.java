package mx.edu.utez.poeta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.edu.utez.poeta.entity.PostulantCV;

public interface IPostulantCVRepository extends JpaRepository<PostulantCV, Long> {
    
    @Query(value = "SELECT * FROM postulant_cv cv WHERE cv.postulant_id = :id", nativeQuery = true)
    public PostulantCV findPostulantCVByUserId(@Param("id") long id);

    @Query(value = "SELECT IF(p.uploaded_cv IS NULL, false, true) FROM postulant_cv p WHERE p.postulant_id = :id", nativeQuery = true)
    public int postulantHasUploadedCv(@Param("id") long id);

}
