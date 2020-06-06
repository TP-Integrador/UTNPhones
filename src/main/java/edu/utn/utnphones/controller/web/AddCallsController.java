package edu.utn.utnphones.controller.web;

import edu.utn.utnphones.controller.CallController;
import edu.utn.utnphones.dto.CallDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/calls")
public class AddCallsController {

    private CallController callController;

    @Autowired
    public AddCallsController(CallController callController) {
        this.callController = callController;
    }

    @PostMapping
    public ResponseEntity addCall(@RequestHeader("User") String user, @RequestHeader("Pass") String pass, @RequestBody @Valid CallDto callDto) throws ParseException {
        ResponseEntity responseEntity;
        if(user.matches("infra") && pass.matches("1234")){
            //TODO ver excepciones Stored Procedure
            Date dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(callDto.getDate());
            callController.addCall(callDto.getLineFrom(), callDto.getLineTo(), callDto.getSeg(), dateTime);
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(callDto);
        }else{
            responseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return responseEntity;
    }

    //TODO Test

}
