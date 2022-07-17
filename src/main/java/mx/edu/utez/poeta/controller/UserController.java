package mx.edu.utez.poeta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.utez.poeta.entity.User;
import mx.edu.utez.poeta.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    public User findUsersById(@PathVariable("id") long id) {
        return userService.findUserById(id);
    }

    @RequestMapping(name = "/delete/{id}", method = {RequestMethod.GET})
    public boolean delete(@PathVariable("id") long id) {
        return userService.delete(id);
    }
}
