package edu.utn.utnphones.service;

import edu.utn.utnphones.dao.LineTypeDao;
import edu.utn.utnphones.domain.LineType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LineTypeService {

    private LineTypeDao lineTypeDao;

    @Autowired
    public LineTypeService(LineTypeDao lineTypeDao) {
        this.lineTypeDao = lineTypeDao;
    }

    public List<LineType> getAll(){
        return lineTypeDao.findAll();
    }

    public void add(LineType lineType){
        lineTypeDao.save(lineType);
    }

}