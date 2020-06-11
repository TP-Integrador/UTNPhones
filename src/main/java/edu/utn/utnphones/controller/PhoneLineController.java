package edu.utn.utnphones.controller;

import edu.utn.utnphones.domain.PhoneLine;
import edu.utn.utnphones.exception.PhoneLineAlreadyExistsException;
import edu.utn.utnphones.exception.PhoneLineNotExistException;
import edu.utn.utnphones.exception.PhoneLineRemovedException;
import edu.utn.utnphones.service.PhoneLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.sql.SQLException;

@Controller
public class PhoneLineController {

    private PhoneLineService linePhoneService;

    @Autowired
    public PhoneLineController(PhoneLineService linePhoneService) {
        this.linePhoneService = linePhoneService;
    }


    public PhoneLine addPhone(PhoneLine phoneLine) throws PhoneLineAlreadyExistsException, SQLException {
        return linePhoneService.addPhone(phoneLine);
    }

    public PhoneLine getById(int id) throws PhoneLineNotExistException {
        return linePhoneService.getById(id);
    }

    public PhoneLine getByNumber(String line) throws PhoneLineNotExistException {
        return linePhoneService.getByNumber(line);
    }

    public void updateStatus(PhoneLine phoneLine, int idphone) throws PhoneLineNotExistException{
        linePhoneService.updateStatus(phoneLine,idphone);
    }

    public void delete(int idphone) throws PhoneLineRemovedException, PhoneLineNotExistException {
        linePhoneService.delete(idphone);
    }


}
