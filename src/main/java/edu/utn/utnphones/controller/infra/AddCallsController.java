package edu.utn.utnphones.controller.infra;

import edu.utn.utnphones.config.LoginInfra;
import edu.utn.utnphones.controller.CallController;
import edu.utn.utnphones.controller.PhoneLineController;
import edu.utn.utnphones.domain.PhoneLine;
import edu.utn.utnphones.dto.CallDto;
import edu.utn.utnphones.exception.PhoneLineNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



@org.springframework.context.annotation.Configuration
@PropertySource("infra.properties")
@RestController
@RequestMapping("infra/calls")
public class AddCallsController {

    private CallController callController;
    private PhoneLineController phoneLineController;
    private LoginInfra loginInfra;

    @Autowired
    public AddCallsController(CallController callController, PhoneLineController phoneLineController, LoginInfra loginInfra) {
        this.callController = callController;
        this.phoneLineController = phoneLineController;
        this.loginInfra = loginInfra;
    }


    @PostMapping
    public ResponseEntity addCall(@RequestHeader("User") String user, @RequestHeader("Pass") String pass, @RequestBody @Valid CallDto callDto) throws ParseException, PhoneLineNotExistException, SQLException {
        ResponseEntity responseEntity = null;
        if (user.matches(loginInfra.getUserconfig()) && pass.matches(loginInfra.getPassconfig())){
            PhoneLine from = phoneLineController.getByNumber(callDto.getLineFrom());
            PhoneLine to = phoneLineController.getByNumber(callDto.getLineTo());
            Date dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(callDto.getDate());
            callController.addCall(callDto.getLineFrom(), callDto.getLineTo(), callDto.getSeg(), dateTime);
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(null);
        }else{
            responseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return responseEntity;
    }

}
