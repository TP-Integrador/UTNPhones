package edu.utn.utnphones.controller;

import edu.utn.utnphones.domain.Province;
import edu.utn.utnphones.exception.ProvinceAlreadyExistsException;
import edu.utn.utnphones.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/province")
public class ProvinceController {

    private ProvinceService provinceService;

    @Autowired
    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @GetMapping("/")
    public List<Province> getAllProvinces(){
        return provinceService.getAll();
    }

    @PostMapping("/")
    public void addProvince(@RequestBody @Valid Province p) throws ProvinceAlreadyExistsException {
        provinceService.add(p);
    }





}
