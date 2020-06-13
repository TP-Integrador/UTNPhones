package edu.utn.utnphones.service;


import edu.utn.utnphones.dao.PhoneLineDao;
import edu.utn.utnphones.domain.PhoneLine;
import edu.utn.utnphones.exception.PhoneLineAlreadyExistsException;
import edu.utn.utnphones.exception.PhoneLineNotExistException;
import edu.utn.utnphones.exception.PhoneLineRemovedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class PhoneLineService {

    private PhoneLineDao linePhoneDao;

    @Autowired
    public PhoneLineService(PhoneLineDao linePhoneDao){
        this.linePhoneDao = linePhoneDao;
    }

    public PhoneLine addPhone(PhoneLine phoneLine) throws PhoneLineAlreadyExistsException, SQLException {
        try {
            PhoneLine ph = linePhoneDao.findByNumber(phoneLine.getLineNumber());
            if (ph != null) {
                throw new PhoneLineAlreadyExistsException();
            }
            return linePhoneDao.save(phoneLine);
        }catch (Exception e){
            throw new SQLException(e);
        }
    }

    public PhoneLine getById(int id) throws PhoneLineNotExistException {
        PhoneLine phoneLine =  linePhoneDao.findById(id).orElseThrow(()-> new PhoneLineNotExistException(""));
        return phoneLine;
    }

    public PhoneLine getByNumber(String line) throws PhoneLineNotExistException {
        PhoneLine phoneLine = linePhoneDao.findByNumber(line);
        if(phoneLine  == null){
            throw new PhoneLineNotExistException(line);
        }
        return phoneLine;
    }

    public void updateStatus(PhoneLine phoneLine, int idphone) throws PhoneLineNotExistException, PhoneLineRemovedException {
        PhoneLine ph = linePhoneDao.findById(idphone).orElseThrow(() -> new PhoneLineNotExistException(""));
        if(ph.getLineStatus() == PhoneLine.Status.Inactive){
            throw new PhoneLineRemovedException();
        }
        linePhoneDao.updateStatus(phoneLine.getLineStatus().toString(),idphone);
    }

    public void delete(int idphone) throws PhoneLineNotExistException, PhoneLineRemovedException {
        PhoneLine ph = linePhoneDao.findById(idphone).orElseThrow(() -> new PhoneLineNotExistException(""));
        if(ph.getLineStatus() == PhoneLine.Status.Inactive){
            throw new PhoneLineRemovedException();
        }
        String status = PhoneLine.Status.Inactive.toString();
        linePhoneDao.updateStatus(status, idphone);
    }
}
