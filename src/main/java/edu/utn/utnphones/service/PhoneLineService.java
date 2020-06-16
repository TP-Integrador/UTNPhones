package edu.utn.utnphones.service;


import com.sun.xml.bind.v2.runtime.CompositeStructureBeanInfo;
import edu.utn.utnphones.dao.PhoneLineDao;
import edu.utn.utnphones.domain.PhoneLine;
import edu.utn.utnphones.domain.PhoneLine.Status;
import edu.utn.utnphones.dto.StatusPhoneDto;
import edu.utn.utnphones.exception.PhoneLineAlreadyExistsException;
import edu.utn.utnphones.exception.PhoneLineNotExistException;
import edu.utn.utnphones.exception.PhoneLineRemovedException;
import edu.utn.utnphones.exception.StatusNotExistsException;
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

    //TODO verificar si el usuario es Client
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

    //TODO verificar si el usuario es Client
    public void updateStatus(StatusPhoneDto statusPhoneDto, int idphone) throws PhoneLineNotExistException, PhoneLineRemovedException, StatusNotExistsException {
        PhoneLine ph = linePhoneDao.findById(idphone).orElseThrow(() -> new PhoneLineNotExistException(""));
        if(ph.getLineStatus() == Status.Inactive){
            throw new PhoneLineRemovedException();
        }
        String status = statusPhoneDto.getStatus();
        if(!(status.matches(Status.Active.toString()) ||
                status.matches(Status.Inactive.toString()) ||
                status.matches(Status.Suspended.toString()))){
            throw new StatusNotExistsException();
        }
        linePhoneDao.updateStatus(statusPhoneDto.getStatus(),idphone);
    }

    public void delete(int idphone) throws PhoneLineNotExistException, PhoneLineRemovedException {
        PhoneLine ph = linePhoneDao.findById(idphone).orElseThrow(() -> new PhoneLineNotExistException(""));
        if(ph.getLineStatus() == Status.Inactive){
            throw new PhoneLineRemovedException();
        }
        String status = Status.Inactive.toString();
        linePhoneDao.updateStatus(status, idphone);
    }
}
