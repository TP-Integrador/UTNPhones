package edu.utn.utnphones.service;

import edu.utn.utnphones.dao.CallDao;
import edu.utn.utnphones.domain.Call;
import edu.utn.utnphones.projections.MostCalledCities;
import edu.utn.utnphones.exception.ResourcesNotExistException;
import edu.utn.utnphones.projections.GetCalls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
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

    public void add(String lineFrom, String lineTo, int seg , Date dateTime) throws SQLException {
        try {
            callDao.addCall(lineFrom, lineTo, seg, dateTime);
        }catch (Exception e){
            throw new SQLException();
        }
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
