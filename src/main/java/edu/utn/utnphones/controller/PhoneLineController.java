package edu.utn.utnphones.controller;

import edu.utn.utnphones.domain.PhoneLine;
import edu.utn.utnphones.dto.StatusPhoneDto;
import edu.utn.utnphones.exception.*;
import edu.utn.utnphones.service.PhoneLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PhoneLineController {

    private PhoneLineService linePhoneService;

    @Autowired
    public PhoneLineController(PhoneLineService linePhoneService) {
        this.linePhoneService = linePhoneService;
    }


    public PhoneLine getById(int id) throws PhoneLineNotExistException {
        return linePhoneService.getById(id);
    }

    public PhoneLine getByNumber(String line) throws PhoneLineNotExistException {
        return linePhoneService.getByNumber(line);
    }

    public PhoneLine addPhone(PhoneLine phoneLine) throws PhoneLineAlreadyExistsException, UserNotexistException {
        return linePhoneService.addPhone(phoneLine);
    }

    public void updateStatus(StatusPhoneDto statusPhoneDto, int idphone) throws PhoneLineNotExistException, PhoneLineRemovedException, StatusNotExistsException {
        linePhoneService.updateStatus(statusPhoneDto, idphone);
    }

    public void delete(int idphone) throws PhoneLineRemovedException, PhoneLineNotExistException {
        linePhoneService.delete(idphone);
    }


}
