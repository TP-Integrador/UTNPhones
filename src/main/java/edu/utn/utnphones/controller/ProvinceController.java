package edu.utn.utnphones.controller;

import edu.utn.utnphones.domain.Province;
import edu.utn.utnphones.exception.ResourceAlreadyExistsException;
import edu.utn.utnphones.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProvinceController {

    private ProvinceService provinceService;

    @Autowired
    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    public List<Province> getAllProvinces(){
        return provinceService.getAll();
    }

    public void addProvince(Province p) throws ResourceAlreadyExistsException {
        provinceService.add(p);
    }
}
