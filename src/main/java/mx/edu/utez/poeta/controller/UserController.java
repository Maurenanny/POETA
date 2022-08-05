package mx.edu.utez.poeta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping(value = "/login")
    public ResponseEntity<?> createTokenAuthentication(@RequestBody User user) {
        return userService.createTokenAuthentication(user);
    }

    @PostMapping(value = "/session")
    public boolean verifySession(@RequestBody User user) {
        return userService.verifySession(user);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<?> logout(@RequestBody User user) {
        return userService.createTokenAuthentication(user);
    }
}
