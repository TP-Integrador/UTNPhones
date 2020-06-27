package edu.utn.utnphones.controller.client;

import edu.utn.utnphones.controller.CallController;
import edu.utn.utnphones.domain.User;
import edu.utn.utnphones.exception.UserNotexistException;
import edu.utn.utnphones.projections.GetCalls;
import edu.utn.utnphones.projections.MostCalledCities;
import edu.utn.utnphones.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/calls")
public class CheckCallsController {

    private CallController callController;
    private SessionManager sessionManager;

    @Autowired
    public CheckCallsController(CallController callController, SessionManager sessionManager) {
        this.callController = callController;
        this.sessionManager = sessionManager;
    }

    @GetMapping
    public ResponseEntity<List<GetCalls>> getCalls(@RequestHeader("Authorization") String sessionToken, @RequestParam(value = "from", required = false) String from, @RequestParam(value = "to", required = false) String to) throws UserNotexistException, ParseException {
        ResponseEntity<List<GetCalls>> responseEntity = null;
        User currentUser = getCurrentUser(sessionToken);
        List<GetCalls> callList = null;
        if (from == null && to == null) {
            callList = callController.getCallsByClient(currentUser.getUserId());
        } else if ((from != null) && (to != null)) {
            Date dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse(from);
            Date dateTo = new SimpleDateFormat("yyyy-MM-dd").parse(to);
            callList = callController.getCallsByDate(dateFrom, dateTo, currentUser.getUserId());
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (responseEntity == null) {
            if (!callList.isEmpty())
                responseEntity = ResponseEntity.ok().body(callList);
            else
                responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return responseEntity;
    }

    @GetMapping("/mostCalledCities")
    public ResponseEntity<List<MostCalledCities>> getMostCalledCities(@RequestHeader("Authorization") String
                                                                              sessionToken) throws UserNotexistException {
        ResponseEntity<List<MostCalledCities>> responseEntity = null;
        User currentUser = getCurrentUser(sessionToken);
        List<MostCalledCities> mostCalledCities = callController.getMostCalledCities(currentUser.getUserId());
        if (mostCalledCities.size() > 0) {
            responseEntity = ResponseEntity.ok(mostCalledCities);
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return responseEntity;
    }

    private User getCurrentUser(String sessionToken) throws UserNotexistException {
        return Optional.ofNullable(sessionManager.getCurrentUser(sessionToken)).orElseThrow(UserNotexistException::new);
    }

}
