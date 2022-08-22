package mx.edu.utez.poeta.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "process")
public class PostulantProcess implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vacant_id", nullable = false)
    private Vacancies vacant;

    @ManyToOne
    @JoinColumn(name = "postulant_id", nullable = false)
    private User postulant;

    @Column(name = "status", nullable = false)
    private int status;

    @Column(name = "favorite", nullable = false)
    private boolean favorite;

    public PostulantProcess() {
        this.status = 1;
        this.favorite = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vacancies getVacant() {
        return vacant;
    }

    public void setVacant(Vacancies vacant) {
        this.vacant = vacant;
    }

    public User getPostulant() {
        return postulant;
    }

    public void setPostulant(User postulant) {
        this.postulant = postulant;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
    
}
