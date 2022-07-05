package mx.edu.utez.poeta.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.poeta.entity.Process;
import mx.edu.utez.poeta.repository.IProcessRepository;

@Service
@Transactional
public class ProcessService {

    private IProcessRepository processRepository;

    @Transactional(readOnly = true)
    public List<Process> findAllProcesses() {
        return processRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Process findProcessById(long id) {
        return processRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public List<Process> findAllUserProcesses(long id) {
        return processRepository.findAllProcessesByUserId(id);
    }

    @Transactional(readOnly = true)
    public List<Process> findAllUserFavoriteProcesses(long id) {
        return processRepository.findAllFavoriteProcessesByUserId(id);
    }

    public boolean save(Process obj) {
        boolean flag = false;
        Process tmp = processRepository.save(obj);
        if (tmp != null) {
            flag = true;
        }
        return flag;
    }

    public boolean delete(long id) {
        boolean flag = false;
        Process tmp = findProcessById(id);
        if (tmp != null) {
            processRepository.delete(tmp);
            flag = true;
        }
        return flag;
    }
    
}
