package edu.utn.utnphones.controller.backoffice;

import edu.utn.utnphones.controller.CallController;
import edu.utn.utnphones.exception.UserNotexistException;
import edu.utn.utnphones.projections.GetCalls;
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

    @Autowired
    public CheckCallsClientController(CallController callController) {
        this.callController = callController;
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<List<GetCalls>> getCalls(@RequestParam(value = "from", required = false) String from, @RequestParam(value = "to", required = false) String to, @PathVariable Integer id) throws UserNotexistException, ParseException {
        ResponseEntity<List<GetCalls>> responseEntity = null;
        List<GetCalls> callList = null;
        if (from == null && to == null && id > 0) {
            callList = callController.getCallsByClient(id);
        } else {
            if ((from != null) && (to != null)) {
                Date dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse(from);
                Date dateTo = new SimpleDateFormat("yyyy-MM-dd").parse(to);
                callList = callController.getCallsByDate(dateFrom, dateTo, id);
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
        if (responseEntity == null) {
            if (!callList.isEmpty()) {
                responseEntity = ResponseEntity.ok().body(callList);
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        }
        return responseEntity;
    }
}
