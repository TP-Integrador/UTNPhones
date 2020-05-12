package edu.utn.utnphones.controller;

import edu.utn.utnphones.domain.PhoneLine;
import edu.utn.utnphones.service.PhoneLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PhoneLineController {

    private PhoneLineService linePhoneService;

    @Autowired
    public PhoneLineController(PhoneLineService linePhoneService) {
        this.linePhoneService = linePhoneService;
    }

    public List<PhoneLine> getLinePhones(){
        return linePhoneService.getAll();
    }

    public void addLinePhone(PhoneLine linePhone){
        linePhoneService.add(linePhone);
    }
    
}
