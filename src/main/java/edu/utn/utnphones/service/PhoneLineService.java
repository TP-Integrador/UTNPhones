package edu.utn.utnphones.service;


import edu.utn.utnphones.dao.PhoneLineDao;
import edu.utn.utnphones.dao.UserDao;
import edu.utn.utnphones.domain.PhoneLine;
import edu.utn.utnphones.domain.PhoneLine.Status;
import edu.utn.utnphones.domain.User;
import edu.utn.utnphones.dto.StatusPhoneDto;
import edu.utn.utnphones.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PhoneLineService {

    private PhoneLineDao linePhoneDao;
    private UserDao userDao;

    @Autowired
    public PhoneLineService(PhoneLineDao linePhoneDao, UserDao userDao) {
        this.linePhoneDao = linePhoneDao;
        this.userDao = userDao;
    }

    public PhoneLine addPhone(PhoneLine phoneLine) throws PhoneLineAlreadyExistsException, UserNotexistException {
        PhoneLine ph = linePhoneDao.findByNumber(phoneLine.getLineNumber());
        if (ph != null) {
            throw new PhoneLineAlreadyExistsException();
        }
        Optional<User> user = userDao.findById(phoneLine.getUser().getUserId());
        if (user.get() == null || !user.get().getUserType().getType().matches("Client")) {
            throw new UserNotexistException();
        }
        return linePhoneDao.save(phoneLine);
    }

    public PhoneLine getById(int id) throws PhoneLineNotExistException {
        PhoneLine phoneLine = linePhoneDao.findById(id).orElseThrow(() -> new PhoneLineNotExistException(""));
        return phoneLine;
    }

    public PhoneLine getByNumber(String line) throws PhoneLineNotExistException {
        PhoneLine phoneLine = linePhoneDao.findByNumber(line);
        if (phoneLine == null) {
            throw new PhoneLineNotExistException(line);
        }
        return phoneLine;
    }

    public void updateStatus(StatusPhoneDto statusPhoneDto, int idphone) throws PhoneLineNotExistException, PhoneLineRemovedException, StatusNotExistsException {
        PhoneLine ph = linePhoneDao.findById(idphone).orElseThrow(() -> new PhoneLineNotExistException(""));
        if (ph.getLineStatus() == Status.Inactive) {
            throw new PhoneLineRemovedException();
        }
        String status = statusPhoneDto.getStatus();
        if (!(status.matches(Status.Active.toString()) ||
                status.matches(Status.Inactive.toString()) ||
                status.matches(Status.Suspended.toString()))) {
            throw new StatusNotExistsException();
        }
        linePhoneDao.updateStatus(statusPhoneDto.getStatus(), idphone);
    }

    public void delete(int idphone) throws PhoneLineNotExistException, PhoneLineRemovedException {
        PhoneLine ph = linePhoneDao.findById(idphone).orElseThrow(() -> new PhoneLineNotExistException(""));
        if (ph.getLineStatus() == Status.Inactive) {
            throw new PhoneLineRemovedException();
        }
        String status = Status.Inactive.toString();
        linePhoneDao.updateStatus(status, idphone);
    }
}
