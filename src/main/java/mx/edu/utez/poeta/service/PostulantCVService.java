package mx.edu.utez.poeta.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.poeta.entity.PostulantCV;
import mx.edu.utez.poeta.repository.IPostulantCVRepository;

@Service
@Transactional
public class PostulantCVService {

    @Autowired
    private IPostulantCVRepository postulantCVRepository;

    @Transactional(readOnly = true)
    public List<PostulantCV> findAllPostulantantCVs() {
        return postulantCVRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PostulantCV findPostulantCVById(long id) {
        return postulantCVRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public PostulantCV findPostulantCVByUserId(long id) {
        return postulantCVRepository.findPostulantCVByUserId(id);
    }

    public boolean save(PostulantCV obj) {
        boolean flag = false;
        PostulantCV tmp = postulantCVRepository.save(obj);
        if (tmp != null) {
            flag = true;
        }
        return flag;
    }

    public boolean delete(long id) {
        boolean flag = false;
        PostulantCV tmp = findPostulantCVById(id);
        if (tmp != null) {
            postulantCVRepository.delete(tmp);
            flag = true;
        }
        return flag;
    }
    
}
