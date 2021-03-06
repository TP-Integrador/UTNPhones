package edu.utn.utnphones.controller.client;

import edu.utn.utnphones.controller.InvoiceController;
import edu.utn.utnphones.domain.Invoice;
import edu.utn.utnphones.domain.User;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/invoices")
public class CheckInvoicesController {
    private InvoiceController invoiceController;
    private SessionManager sessionManager;

    @Autowired
    public CheckInvoicesController(InvoiceController invoiceController, SessionManager sessionManager) {
        this.invoiceController = invoiceController;
        this.sessionManager = sessionManager;
    }

    @GetMapping
    public ResponseEntity<List<Invoice>> getInvoices(@RequestHeader("Authorization") String sessionToken, @RequestParam(value = "from", required = false) String from, @RequestParam(value = "to", required = false) String to) throws UserNotexistException, ParseException {
        ResponseEntity<List<Invoice>> responseEntity = null;
        User currentUser = getCurrentUser(sessionToken);
        List<Invoice> invoicesList = null;
        if (from == null && to == null) {
            invoicesList = invoiceController.getInvoicesByClient(currentUser.getUserId());
        } else if ((from != null) && (to != null)) {
            Date dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse(from);
            Date dateTo = new SimpleDateFormat("yyyy-MM-dd").parse(to);
            invoicesList = invoiceController.getInvoicesByDate(dateFrom, dateTo, currentUser.getUserId());
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (responseEntity == null) {
            if (!invoicesList.isEmpty()) {
                responseEntity = ResponseEntity.ok().body(invoicesList);
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        }
        return responseEntity;
    }

    private User getCurrentUser(String sessionToken) throws UserNotexistException {
        return Optional.ofNullable(sessionManager.getCurrentUser(sessionToken)).orElseThrow(UserNotexistException::new);
    }

}
