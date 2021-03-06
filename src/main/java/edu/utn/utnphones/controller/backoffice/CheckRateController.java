package edu.utn.utnphones.controller.backoffice;

import edu.utn.utnphones.controller.CityController;
import edu.utn.utnphones.controller.RateController;
import edu.utn.utnphones.domain.City;
import edu.utn.utnphones.domain.Rate;
import edu.utn.utnphones.exception.CityNotexistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("backoffice/rates")
public class CheckRateController {

    private RateController rateController;
    private CityController cityController;

    @Autowired
    public CheckRateController(RateController rateController, CityController cityController) {
        this.rateController = rateController;
        this.cityController = cityController;
    }

    @GetMapping
    public ResponseEntity<List<Rate>> getAllRates() {
        ResponseEntity<List<Rate>> responseEntity = null;
        List<Rate> rateList = rateController.getAllRates();
        if (!rateList.isEmpty()) {
            responseEntity = ResponseEntity.ok().body(rateList);
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return responseEntity;
    }

    @GetMapping("/{idFrom}")
    public ResponseEntity<List<Rate>> getByIdFrom(@PathVariable int idFrom) throws CityNotexistException {
        ResponseEntity<List<Rate>> responseEntity = null;
        City from = cityController.getById(idFrom);
        List<Rate> rateList = rateController.getByIdFrom(idFrom);
        if (!rateList.isEmpty()) {
            responseEntity = ResponseEntity.ok().body(rateList);
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return responseEntity;
    }

    @GetMapping("/{idFrom}/{idTo}")
    public ResponseEntity<Rate> getRate(@PathVariable int idFrom, @PathVariable int idTo) throws CityNotexistException {
        ResponseEntity<Rate> responseEntity = null;
        City from = cityController.getById(idFrom);
        City to = cityController.getById(idTo);
        Rate rate = rateController.getRate(idFrom, idTo);
        if ((rate != null)) {
            responseEntity = ResponseEntity.ok().body(rate);
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return responseEntity;
    }

}
