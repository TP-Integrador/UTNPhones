package edu.utn.utnphones.service;

import edu.utn.utnphones.dao.ProvinceDao;
import edu.utn.utnphones.domain.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceService {

    ProvinceDao provinceDao;

    @Autowired
    public ProvinceService(ProvinceDao provinceDao) {
        this.provinceDao = provinceDao;
    }

    public void add(Province province) throws ProvinceAlreadyExistsException {
        provinceDao.save(province);
    }

    public List<Province> getAll() {
        return provinceDao.findAll();
    }
}
