package edu.utn.utnphones.controller;

import edu.utn.utnphones.domain.LineType;
import edu.utn.utnphones.service.LineTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/lineTypes")
public class LineTypeController {

    private LineTypeService lineTypeService;

    @Autowired
    public LineTypeController(LineTypeService lineTypeService) {
        this.lineTypeService = lineTypeService;
    }

    @GetMapping("/")
    public List<LineType> getLineTypes(){
        return lineTypeService.getAll();
    }

    @PostMapping("/")
    public void add(@RequestBody @Valid LineType lineType){
        lineTypeService.add(lineType);
    }
}
