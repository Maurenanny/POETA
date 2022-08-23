package mx.edu.utez.poeta.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.poeta.entity.PostulantProcess;
import mx.edu.utez.poeta.repository.IPostulantProcessRepository;

@Service
@Transactional
public class PostulantProcessService {

    @Autowired
    private IPostulantProcessRepository postulantProcessRepository;

    @Transactional(readOnly = true)
    public List<PostulantProcess> findAllProcesses() {
        return postulantProcessRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PostulantProcess findProcessById(long id) {
        return postulantProcessRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public List<PostulantProcess> findAllUserProcesses(long id) {
        return postulantProcessRepository.findAllProcessesByUserId(id);
    }

    @Transactional(readOnly = true)
    public List<PostulantProcess> findAllUserFavoriteProcesses(long id) {
        return postulantProcessRepository.findAllFavoriteProcessesByUserId(id);
    }

    public PostulantProcess save(PostulantProcess obj) {
        PostulantProcess tmp = postulantProcessRepository.save(obj);
        if (tmp != null) {
            return tmp;
        }
        return null;
    }

    public boolean delete(long id) {
        boolean flag = false;
        PostulantProcess tmp = findProcessById(id);
        if (tmp != null) {
            postulantProcessRepository.delete(tmp);
            flag = true;
        }
        return flag;
    }

    public List<PostulantProcess> findPostulantProcessesByFilter(long id, int type) {
        switch (type) {
            case 1:
            return postulantProcessRepository.findAllProcessesByUserId(id);
            case 2:
            return postulantProcessRepository.findAllFavoriteProcessesByUserId(id);
            case 3:
            return postulantProcessRepository.findAllRejectedProcessesByUserId(id);
            case 4:
            return postulantProcessRepository.findAllAcceptedProcessesByUserId(id);
        }
        return null;
    }
    
}
