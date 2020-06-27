package edu.utn.utnphones.controller.backoffice;

import edu.utn.utnphones.controller.InvoiceController;
import edu.utn.utnphones.domain.Invoice;
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

    @Autowired
    public CheckInvoicesClientController(InvoiceController invoiceController) {
        this.invoiceController = invoiceController;
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<List<Invoice>> getInvoices(@RequestParam(value = "from", required = false) String from, @RequestParam(value = "to", required = false) String to, @PathVariable Integer id) throws ParseException {
        ResponseEntity<List<Invoice>> responseEntity = null;
        List<Invoice> callList = null;
        if (from == null && to == null && id > 0) {
            callList = invoiceController.getInvoicesByClient(id);
        } else {
            if ((from != null) && (to != null)) {
                Date dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse(from);
                Date dateTo = new SimpleDateFormat("yyyy-MM-dd").parse(to);
                callList = invoiceController.getInvoicesByDate(dateFrom, dateTo, id);
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
