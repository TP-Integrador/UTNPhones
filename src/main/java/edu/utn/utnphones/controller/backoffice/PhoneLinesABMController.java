package edu.utn.utnphones.controller.backoffice;

import edu.utn.utnphones.controller.PhoneLineController;
import edu.utn.utnphones.domain.PhoneLine;
import edu.utn.utnphones.dto.StatusPhoneDto;
import edu.utn.utnphones.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<PhoneLine> getPhonelineById(@PathVariable int idphone) throws PhoneLineNotExistException {
        ResponseEntity<PhoneLine> responseEntity = null;
        PhoneLine phoneLine = phoneLineController.getById(idphone);
        return ResponseEntity.ok().body(phoneLine);
    }


    @PostMapping
    public ResponseEntity addPhoneLine(@RequestBody @Valid PhoneLine phoneLine) throws PhoneLineAlreadyExistsException, UserNotexistException {
        PhoneLine ph = phoneLineController.addPhone(phoneLine);
        return ResponseEntity.created(getLocation(ph)).build();
    }


    @PutMapping("/{idphone}")
    public ResponseEntity UpdatePhoneline(@PathVariable int idphone, @RequestBody StatusPhoneDto statusPhoneDto) throws PhoneLineNotExistException, PhoneLineRemovedException, StatusNotExistsException {
        phoneLineController.updateStatus(statusPhoneDto,idphone);
        return ResponseEntity.ok().build();
     }

    @DeleteMapping("/{idphone}")
    public ResponseEntity<?> deletePhoneLine(@PathVariable int idphone) throws PhoneLineNotExistException, PhoneLineRemovedException {
        phoneLineController.delete(idphone);
        return ResponseEntity.ok().build();
    }


    public URI getLocation(PhoneLine phoneLine) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(phoneLine.getId())
                .toUri();
    }
}
