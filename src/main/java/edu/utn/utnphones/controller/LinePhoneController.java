package edu.utn.utnphones.controller;

import edu.utn.utnphones.domain.LinePhone;
import edu.utn.utnphones.service.LinePhoneService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.Line;
import java.util.List;

@RestController
@RequestMapping("/linePhones")
public class LinePhoneController {

    private LinePhoneService linePhoneService;

    @Autowired
    public LinePhoneController(LinePhoneService linePhoneService) {
        this.linePhoneService = linePhoneService;
    }

    @GetMapping("/")
    public List<LinePhone> getLinePhones(){
        return linePhoneService.getAll();
    }

    @PostMapping("/")
    public void addLinePhone(LinePhone linePhone){
        linePhoneService.add(linePhone);
    }
    
}
