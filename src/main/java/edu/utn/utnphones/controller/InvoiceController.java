package edu.utn.utnphones.controller;

import edu.utn.utnphones.domain.Invoice;
import edu.utn.utnphones.exception.ResourcesNotExistException;
import edu.utn.utnphones.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;

@Controller
public class InvoiceController {
    private InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    public List<Invoice> getAllInvoice() {
        return invoiceService.getAll();
    }

    public Invoice getById(int id) throws ResourcesNotExistException {
        return invoiceService.getById(id);
    }

    public Invoice addInvoice(Invoice invoice) {
        return invoiceService.add(invoice);
    }

    public List<Invoice> getInvoicesByDate(Date from, Date to, int userId) {
        return invoiceService.getInvoicesByDate(from, to, userId);
    }

    public List<Invoice> getInvoicesByClient(int id) {
        return invoiceService.getInvoicesByClient(id);
    }

}

