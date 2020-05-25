package edu.utn.utnphones.service;

import edu.utn.utnphones.dao.CallDao;
import edu.utn.utnphones.domain.Call;
import edu.utn.utnphones.exception.ResourcesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CallService {
    private CallDao callDao;

    @Autowired
    public CallService(CallDao callDao){this.callDao = callDao;}

    public List<Call> getAll(){
        return callDao.findAll();
    }

    public Call add(Call call) {
        return callDao.save(call);
    }

    public Call getById(int id) throws ResourcesNotExistException {
        return callDao.findById(id).orElseThrow(ResourcesNotExistException::new);
    }
}
