package edu.utn.utnphones.controller;

import edu.utn.utnphones.domain.Call;

import edu.utn.utnphones.projections.MostCalledCities;
import edu.utn.utnphones.exception.ResourcesNotExistException;
import edu.utn.utnphones.projections.GetCalls;
import edu.utn.utnphones.service.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Controller
public class CallController {
    private CallService callService;

    @Autowired
    public CallController(CallService callService) {
        this.callService = callService;
    }

    public List<Call> getAllCalls(){
        return callService.getAll();
    }

    public void addCall(String lineFrom, String lineTo, int seg , Date dateTime) throws SQLException {
        callService.add(lineFrom,lineTo,seg,dateTime);
    }

    public Call getById(int id) throws ResourcesNotExistException {
        return callService.getById(id);
    }

    public List<Call> getCallsByDate(Date dateFrom, Date dateTo, int userId){
        return callService.getCallsByDate(dateFrom,dateTo,userId);
    }

    public List<GetCalls> getCallsByClient(int id) {
        return callService.getCallsByClient(id);
    }

    public List<MostCalledCities> getMostCalledCities(int userId){
        return callService.getMostCalledCities(userId);
    }
}
