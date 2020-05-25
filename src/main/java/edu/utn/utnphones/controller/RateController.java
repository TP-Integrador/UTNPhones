package edu.utn.utnphones.controller;

import edu.utn.utnphones.domain.Rate;
import edu.utn.utnphones.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class RateController {
    private RateService rateService;

    @Autowired
    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    public List<Rate> getAllRates(){
        return rateService.getAll();
    }

    public void addRate(Rate rate){
        rateService.add(rate);
    }
}
