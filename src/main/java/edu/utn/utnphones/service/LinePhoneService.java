package edu.utn.utnphones.service;

import edu.utn.utnphones.dao.LinePhoneDao;
import edu.utn.utnphones.domain.LinePhone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinePhoneService {

    private LinePhoneDao linePhoneDao;

    @Autowired
    public LinePhoneService(LinePhoneDao linePhoneDao) {
        this.linePhoneDao = linePhoneDao;
    }

    public List<LinePhone> getAll(){
        return linePhoneDao.findAll();
    }

    public void add(LinePhone linePhone){
        linePhoneDao.save(linePhone);
    }
}
