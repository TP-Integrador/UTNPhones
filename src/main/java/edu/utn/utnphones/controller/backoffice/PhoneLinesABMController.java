package edu.utn.utnphones.controller.backoffice;

import edu.utn.utnphones.controller.PhoneLineController;
import edu.utn.utnphones.domain.PhoneLine;
import edu.utn.utnphones.exception.PhoneLineAlreadyExistsException;
import edu.utn.utnphones.exception.PhoneLineNotExistException;
import edu.utn.utnphones.exception.PhoneLineRemovedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.sql.SQLException;

@RestController
@RequestMapping("backoffice/phonelines")
public class PhoneLinesABMController {

    private PhoneLineController phoneLineController;

    @Autowired
    public PhoneLinesABMController(PhoneLineController phoneLineController) {
        this.phoneLineController = phoneLineController;
    }

    @GetMapping("/{idphone}")
    public ResponseEntity<PhoneLine> getPhonelineById(@RequestHeader("Authorization") String sessionToken, @PathVariable int idphone) throws PhoneLineNotExistException {

        ResponseEntity<PhoneLine> responseEntity = null;
        PhoneLine phoneLine = phoneLineController.getById(idphone);
        if (!(phoneLine == null)) {
            responseEntity = ResponseEntity.ok().body(phoneLine);
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return responseEntity;
    }

    //TODO agregar triggers para Status "Active" al agregar
    // agregar Tests

    @PostMapping
    public ResponseEntity addPhoneLine(@RequestHeader("Authorization") String sessionToken, @RequestBody @Valid PhoneLine phoneLine) throws PhoneLineAlreadyExistsException, SQLException {
        ResponseEntity responseEntity = null;
        PhoneLine ph = phoneLineController.addPhone(phoneLine);
        if (!(ph == null)) {
            responseEntity = ResponseEntity.created(getLocation(ph)).build();
        } else
            responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return responseEntity;
    }

    @PutMapping("/{idphone}")
    public ResponseEntity UpdatePhoneline(@RequestHeader("Authorization") String sessionToken, @PathVariable int idphone, @RequestBody PhoneLine phoneLine) throws PhoneLineNotExistException {
        phoneLineController.updateStatus(phoneLine,idphone);
        return ResponseEntity.ok().build();
     }

    @DeleteMapping("/{idphone}")
    public ResponseEntity<?> deletePhoneLine(@PathVariable int idphone) throws PhoneLineNotExistException, PhoneLineRemovedException {
        phoneLineController.delete(idphone);
        return ResponseEntity.ok().build();
    }


    private URI getLocation(PhoneLine phoneLine) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(phoneLine.getId())
                .toUri();
    }
}
