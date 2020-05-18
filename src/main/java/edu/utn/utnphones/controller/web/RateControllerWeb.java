package edu.utn.utnphones.controller.web;

import edu.utn.utnphones.controller.CityController;
import edu.utn.utnphones.controller.RateController;
import edu.utn.utnphones.domain.Rate;
import edu.utn.utnphones.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/rates")
public class RateControllerWeb {

     private RateController rateController;
     private SessionManager sessionManager;
     private CityController cityController;

    @Autowired
    public RateControllerWeb(RateController rateController, SessionManager sessionManager, CityController cityController) {
        this.rateController = rateController;
        this.sessionManager = sessionManager;
        this.cityController = cityController;
    }

    @GetMapping
    public ResponseEntity<List<Rate>> getAllRates(@RequestHeader("Authorization") String sessionToken){
        return ResponseEntity.ok().body(rateController.getAllRates());
    }

    @PostMapping
    public ResponseEntity addRate(@RequestHeader("Authorization") String sessionToken, @RequestBody @Valid Rate rate ){
        return ResponseEntity.status(HttpStatus.CREATED).body(rateController.addRate(rate));
    }

    @GetMapping("/{idFrom}")
    public ResponseEntity<List<Rate>> getByIdFrom(@RequestHeader("Authorization") String sessionToken, @PathVariable int idFrom)  {
        return ResponseEntity.ok().body(rateController.getByIdFrom(idFrom));
    }

}
