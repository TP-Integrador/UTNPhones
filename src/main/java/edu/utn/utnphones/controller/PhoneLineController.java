package edu.utn.utnphones.controller;



import edu.utn.utnphones.domain.PhoneLine;
import edu.utn.utnphones.service.PhoneLineService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.Line;
import java.util.List;

@RestController
@RequestMapping("/linePhones")
public class PhoneLineController {

    private PhoneLineService linePhoneService;

    @Autowired
    public PhoneLineController(PhoneLineService linePhoneService) {
        this.linePhoneService = linePhoneService;
    }

    @GetMapping("/")
    public List<PhoneLine> getLinePhones(){
        return linePhoneService.getAll();
    }

    @PostMapping("/")
    public void addLinePhone(PhoneLine linePhone){
        linePhoneService.add(linePhone);
    }
    
}
