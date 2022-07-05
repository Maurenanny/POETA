package mx.edu.utez.poeta.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.poeta.entity.Roles;
import mx.edu.utez.poeta.repository.IRolesRepository;

@Service
@Transactional
public class RoleService {
    
    @Autowired
    private IRolesRepository rolesRepository;

    @Transactional(readOnly = true)
    public List<Roles> findAllRoles() {
        return rolesRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Roles findRoleById(long id) {
        return rolesRepository.findById(id).get();
    }

    public boolean save(Roles obj) {
        boolean flag = false;
        Roles tmp = rolesRepository.save(obj);
        if (tmp != null) {
            flag = true;
        }
        return flag;
    }

    public boolean delete(long id) {
        boolean flag = false;
        Roles tmp = findRoleById(id);
        if (tmp != null) {
            rolesRepository.delete(tmp);
            flag = true;
        }
        return flag;
    }

}
