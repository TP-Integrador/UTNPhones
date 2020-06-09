package edu.utn.utnphones.service;


import edu.utn.utnphones.dao.PhoneLineDao;
import edu.utn.utnphones.domain.PhoneLine;
import edu.utn.utnphones.exception.PhoneLineNotExistException;
import edu.utn.utnphones.exception.ResourcesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class PhoneLineService {

    private PhoneLineDao linePhoneDao;

    @Autowired
    public PhoneLineService(PhoneLineDao linePhoneDao) {
        this.linePhoneDao = linePhoneDao;
    }

    //TODO validar si se usan
    /*
    public List<PhoneLine> getAll() {
        return linePhoneDao.findAll();
    }

    public PhoneLine getById(int id) throws ResourcesNotExistException {
        return linePhoneDao.findById(id).orElseThrow(ResourcesNotExistException::new);
    }

    public PhoneLine add(PhoneLine linePhone) {
        return linePhoneDao.save(linePhone);
    }

     */

    public PhoneLine getByNumber(String line) throws PhoneLineNotExistException {
        PhoneLine phoneLine = linePhoneDao.findByNumber(line);
        if(phoneLine  == null){
            throw new PhoneLineNotExistException(line);
        }
        return phoneLine;
    }
}
