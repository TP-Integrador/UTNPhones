package edu.utn.utnphones.service;

import edu.utn.utnphones.dao.CallDao;
import edu.utn.utnphones.domain.Call;
import edu.utn.utnphones.projections.MostCalledCities;
import edu.utn.utnphones.exception.ResourcesNotExistException;
import edu.utn.utnphones.projections.GetCalls;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CallService {
    private CallDao callDao;

    @Autowired
    public CallService(CallDao callDao){this.callDao = callDao;}

    public List<Call> getAll(){
        return callDao.findAll();
    }


    public Call add(Call call) throws GenericJDBCException{
        return callDao.save(call);
    }

    public Call getById(int id) throws ResourcesNotExistException {
        return callDao.findById(id).orElseThrow(ResourcesNotExistException::new);
    }

    public List<GetCalls> getCallsByDate(Date dateFrom, Date dateTo, int userId){
        return callDao.getCallsByDate(dateFrom,dateTo,userId);
    }

    public List<GetCalls> getCallsByClient(int id) {
        return callDao.getCallsByClient(id);
    }

    public List<MostCalledCities> getMostCalledCities ( int userId){
        return callDao.getMostCalledCities(userId);
    }
}
