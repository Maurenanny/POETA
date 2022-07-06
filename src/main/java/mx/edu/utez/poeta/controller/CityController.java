package mx.edu.utez.poeta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.utez.poeta.entity.City;
import mx.edu.utez.poeta.service.CityService;

@RestController
@RequestMapping(path = "/city")
public class CityController {
    
    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public List<City> findAllCities() {
        return cityService.findAllCities();
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    public City findCityById(@PathVariable("id") long id) {
        return cityService.findCityById(id);
    }

    @RequestMapping(name = "/save", method = {RequestMethod.POST})
    public boolean save(@RequestBody City obj) {
        return cityService.save(obj);
    }

    @RequestMapping(name = "/delete/{id}", method = {RequestMethod.GET})
    public boolean delete(@PathVariable("id") long id) {
        return cityService.deleteCityById(id);
    }

}
