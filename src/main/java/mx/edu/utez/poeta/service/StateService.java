package mx.edu.utez.poeta.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.poeta.entity.State;
import mx.edu.utez.poeta.repository.IStateRepository;

@Service
@Transactional
public class StateService {

    @Autowired
    private IStateRepository stateRepository;

    @Transactional(readOnly = true)
    public List<State> findAllStates() {
        return stateRepository.findAll();
    }

    @Transactional(readOnly = true)
    public State findStateById(long id) {
        return stateRepository.findById(id).get();
    }

    public boolean save(State obj) {
        boolean flag = false;
        State tmp = stateRepository.save(obj);
        if (tmp != null) {
            flag = true;
        }
        return flag;
    }

    public boolean delete(long id) {
        boolean flag = false;
        State tmp = findStateById(id);
        if (tmp != null) {
            stateRepository.delete(tmp);
            flag = true;
        }
        return flag;
    }
    
}
