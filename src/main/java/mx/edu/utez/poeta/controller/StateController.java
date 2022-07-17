package mx.edu.utez.poeta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.utez.poeta.entity.State;
import mx.edu.utez.poeta.service.StateService;

@RestController
@RequestMapping(path = "/state")
public class StateController {
    @Autowired
    private StateService stateService;

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public List<State> findAllstates() {
        return stateService.findAllStates();
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    public State findStateById(@PathVariable("id") long id) {
        return stateService.findStateById(id);
    }

    @RequestMapping(name = "/save", method = {RequestMethod.POST})
    public boolean save(@RequestBody State obj) {
        return stateService.save(obj);
    }

    @RequestMapping(name = "/delete/{id}", method = {RequestMethod.GET})
    public boolean delete(@PathVariable("id") long id) {
        return stateService.delete(id);
    }
}
