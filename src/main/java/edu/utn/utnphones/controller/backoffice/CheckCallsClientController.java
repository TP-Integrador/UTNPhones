package edu.utn.utnphones.controller.backoffice;

import edu.utn.utnphones.controller.CallController;
import edu.utn.utnphones.domain.Call;
import edu.utn.utnphones.domain.User;
import edu.utn.utnphones.exception.UserNotexistException;
import edu.utn.utnphones.exception.ValidationException;
import edu.utn.utnphones.projections.GetCalls;
import edu.utn.utnphones.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("backoffice/calls")
public class CheckCallsClientController {
    private CallController callController;
    private SessionManager sessionManager;

    @Autowired
    public CheckCallsClientController(CallController callController, SessionManager sessionManager) {
        this.callController = callController;
        this.sessionManager = sessionManager;
    }

    //TODO: GetCalls (devolver todas las llamadas de todos los clientes) otro endpoint, ver si hace falta.

    @GetMapping("/client/{id}")
    public ResponseEntity<List<GetCalls>> getCallsByClient(@RequestHeader("Authorization") String sessionToken, @PathVariable Integer id){
       ResponseEntity<List<GetCalls>> responseEntity = null;
       if (id != null) {
           List<GetCalls> callList = callController.getCallsByClient(id);
           if (!callList.isEmpty()) {
               responseEntity = ResponseEntity.ok().body(callList);
           } else {
               responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
           }
       }else{
           responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
       }
       return responseEntity;
    }

    @GetMapping("/client/{id}/date")
    public ResponseEntity<List<Call>> getCallsByDate(@RequestHeader ("Authorization") String sessionToken, @RequestParam(value = "from" ) String from, @RequestParam(value = "to") String to, @PathVariable Integer id) throws UserNotexistException, ParseException {
        ResponseEntity<List<Call>> responseEntity = null;
        if ((from != null) && (to != null)) {
            Date dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse(from);
            Date dateTo = new SimpleDateFormat("yyyy-MM-dd").parse(to);
            List<Call> callList = callController.getCallsByDate(dateFrom, dateTo, id);
            if (!callList.isEmpty()) {
                responseEntity = ResponseEntity.ok().body(callList);
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return responseEntity;
    }
}