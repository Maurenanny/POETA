package mx.edu.utez.poeta.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import mx.edu.utez.poeta.entity.Roles;
import mx.edu.utez.poeta.entity.User;
import mx.edu.utez.poeta.service.RoleService;
import mx.edu.utez.poeta.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    private String picName;

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @RequestMapping(value = "/roles", method = {RequestMethod.GET})
    public List<Roles> findAllRoles() {
        return roleService.findAllRoles();
    }

    @RequestMapping(value = "/roles/{id}", method = {RequestMethod.GET})
    public Roles findRoleById(@PathVariable("id") long id) {
        return roleService.findRoleById(id);
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

    @PostMapping("/upload/picture")
    public void handleFileUpload(@RequestParam("file") MultipartFile file) {
        String separator = FileSystems.getDefault().getSeparator();
        String fileName = UUID.randomUUID().toString();
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        try {
            String userDirectory = FileSystems.getDefault().getPath("").toAbsolutePath().toString();
            file.transferTo(new File(userDirectory + "\\src\\main\\resources\\static\\uploads\\" + separator + "profilePics" + separator + fileName + "." + ext));
            this.picName = fileName + "." + ext;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/register")
    public boolean register(@RequestBody User obj) {
        try {
            obj.setImage(picName);
            userService.save(obj);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
