package mx.edu.utez.poeta.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.poeta.entity.City;
import mx.edu.utez.poeta.repository.ICityRepository;

@Service
@Transactional
public class CityService {
    
    @Autowired
    private ICityRepository cityRepository;

    @Transactional(readOnly = true)
    public List<City> findAllCities() {
        return cityRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<City> findAllCitiesByState(long id) {
        return cityRepository.findAllByStateId(id);
    }

    @Transactional(readOnly = true)
    public City findCityById(long id) {
        return cityRepository.findById(id).get();
    }

    public boolean save(City obj) {
        boolean flag = false;
        City tmp = cityRepository.save(obj);
        if (tmp != null) {
            flag = true;
        }
        return flag;
    }

    public boolean deleteCityById(long id) {
        boolean flag = false;
        City tmp = findCityById(id);
        if (tmp != null) {
            cityRepository.delete(tmp);
            flag = true;
        }
        return flag;
    }

}
