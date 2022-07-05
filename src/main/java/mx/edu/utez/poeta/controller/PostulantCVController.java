package mx.edu.utez.poeta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.utez.poeta.entity.PostulantCV;
import mx.edu.utez.poeta.service.PostulantCVService;

@RestController
@RequestMapping(value = "/postulant/cv")
public class PostulantCVController {

    @Autowired
    private PostulantCVService postulantCVService;

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public List<PostulantCV> findAllPostulantsCVs() {
        return postulantCVService.findAllPostulantantCVs();
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    public PostulantCV findPostulantCVById(@PathVariable("id") long id) {
        return postulantCVService.findPostulantCVById(id);
    }

    @RequestMapping(value = "/user/{id}", method = {RequestMethod.GET})
    public PostulantCV findPostulantCVByUserId(@PathVariable("id") long id) {
        return postulantCVService.findPostulantCVByUserId(id);
    }
    
}
