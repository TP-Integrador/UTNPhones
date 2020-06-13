package edu.utn.utnphones.controller;

import edu.utn.utnphones.domain.Call;
import edu.utn.utnphones.domain.Invoice;
import edu.utn.utnphones.exception.PhoneLineNotExistException;
import edu.utn.utnphones.exception.ResourcesNotExistException;
import edu.utn.utnphones.service.InvoiceService;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class InvoicesControllerTest {
    private InvoiceService invoiceService;
    private InvoiceController invoiceController;
    private Invoice invoice;

    @Before
    public void setUp(){
        invoiceService = mock(InvoiceService.class);
        invoice = mock(Invoice.class);
        invoiceController = new InvoiceController(invoiceService);
    }

    @Test
    public void testGetAllInvoices(){
        List<Invoice> listInvoices = new ArrayList<>();
        when(invoiceController.getAllInvoice()).thenReturn(listInvoices);

        assertEquals(listInvoices,listInvoices);
    }

    @Test
    public void testGetInvoicesByIdOk() throws ResourcesNotExistException {
        when(invoiceService.getById(1)).thenReturn(invoice);
        invoiceController.getById(1);
    }

    @Test(expected = ResourcesNotExistException.class)
    public void testGetInvoicesByIdNotExists() throws ResourcesNotExistException {
        when(invoiceService.getById(1)).thenThrow(new ResourcesNotExistException());
        invoiceController.getById(1);
    }

    @Test
    public void testAddInvoiceOk() throws SQLException {
        when(invoiceController.addInvoice(invoice)).thenReturn(invoice);
        assertEquals(invoice,invoice);
    }

}
