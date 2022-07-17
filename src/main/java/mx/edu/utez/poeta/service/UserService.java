package mx.edu.utez.poeta.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.poeta.entity.User;
import mx.edu.utez.poeta.repository.IUserRepository;

@Service
@Transactional
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findUserById(long id) {
        return userRepository.findById(id).get();
    }

    public boolean save(User obj) {
        boolean flag = false;
        User tmp = userRepository.save(obj);
        if (tmp != null) {
            flag = true;
        }
        return flag;
    }

    public boolean delete(long id) {
        boolean flag = false;
        User tmp = findUserById(id);
        if (tmp != null) {
            userRepository.delete(tmp);
            flag = true;
        }
        return flag;
    }
    
}
