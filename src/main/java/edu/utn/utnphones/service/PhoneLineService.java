package edu.utn.utnphones.service;


import edu.utn.utnphones.dao.PhoneLineDao;
import edu.utn.utnphones.domain.PhoneLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneLineService {

    private PhoneLineDao linePhoneDao;

    @Autowired
    public PhoneLineService(PhoneLineDao linePhoneDao) {
        this.linePhoneDao = linePhoneDao;
    }

    public List<PhoneLine> getAll(){
        return linePhoneDao.findAll();
    }

    public void add(PhoneLine linePhone){
        linePhoneDao.save(linePhone);
    }
}
