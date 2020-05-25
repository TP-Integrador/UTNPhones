package edu.utn.utnphones.controller.web;

import edu.utn.utnphones.controller.CityController;
import edu.utn.utnphones.controller.ProvinceController;
import edu.utn.utnphones.controller.RateController;
import edu.utn.utnphones.domain.Province;
import edu.utn.utnphones.exception.ResourcesNotExistException;
import edu.utn.utnphones.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {
    private SessionManager sessionManager;
    private ProvinceController provinceController;
    private CityController cityController;
    private RateController rateController;

    //... Agregar todos los controles y generar Contructor

    @Autowired
    public TestController(SessionManager sessionManager, ProvinceController provinceController, CityController cityController, RateController rateController) {
        this.sessionManager = sessionManager;
        this.provinceController = provinceController;
        this.cityController = cityController;
        this.rateController = rateController;
    }

    //MÉTODOS PROVINCE
    @GetMapping("/province")
    public ResponseEntity<List<Province>> getAll(@RequestHeader("Authorization") String sessionToken){
        return ResponseEntity.ok().body(provinceController.getAllProvinces());
    }

    @GetMapping("/province/{id}")
    public ResponseEntity<Province> getById(@RequestHeader("Authorization") String sessionToken, @PathVariable int id) throws ResourcesNotExistException {
        return ResponseEntity.ok().body(provinceController.getById(id));
    }

    @PostMapping("/province")
    public ResponseEntity addProvince(@RequestHeader("Authorization") String sessionToken, @RequestBody @Valid Province p){
        return ResponseEntity.status(HttpStatus.CREATED).body(provinceController.addProvince(p));
    }

}
