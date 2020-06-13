package edu.utn.utnphones.controller.backoffice;

import edu.utn.utnphones.controller.InvoiceController;
import edu.utn.utnphones.domain.Call;
import edu.utn.utnphones.domain.Invoice;
import edu.utn.utnphones.exception.UserNotexistException;
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
@RequestMapping("backoffice/invoices")
public class CheckInvoicesClientController {
    private InvoiceController invoiceController;
    private SessionManager sessionManager;

    @Autowired
    public CheckInvoicesClientController(InvoiceController invoiceController, SessionManager sessionManager) {
        this.invoiceController = invoiceController;
        this.sessionManager = sessionManager;
    }

    //TODO: GetInvoices (devolver todas las facturas de todos los clientes) otro endpoint, ver si hace falta.

    @GetMapping("/client/{id}")
    public ResponseEntity<List<Invoice>> getInvoicesByClient(@RequestHeader("Authorization") String sessionToken, @PathVariable Integer id){
        ResponseEntity<List<Invoice>> responseEntity = null;
        if (id != null) {
            List<Invoice> callList = invoiceController.getInvoicesByClient(id);
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
    public ResponseEntity<List<Invoice>> getInvoicesByDate(@RequestHeader ("Authorization") String sessionToken, @RequestParam(value = "from" ) String from, @RequestParam(value = "to") String to, @PathVariable Integer id) throws UserNotexistException, ParseException {
        ResponseEntity<List<Invoice>> responseEntity = null;
        if ((from != null) && (to != null)) {
            Date dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse(from);
            Date dateTo = new SimpleDateFormat("yyyy-MM-dd").parse(to);
            List<Invoice> callList = invoiceController.getInvoicesByDate(dateFrom, dateTo, id);
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

    //TODO: hacer test de controllerweb, controller y service
}
