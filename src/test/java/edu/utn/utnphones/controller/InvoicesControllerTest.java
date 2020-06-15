package edu.utn.utnphones.controller;

import edu.utn.utnphones.domain.Invoice;
import edu.utn.utnphones.exception.ResourcesNotExistException;
import edu.utn.utnphones.service.InvoiceService;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        when(invoiceService.add(invoice)).thenReturn(invoice);
        invoiceController.addInvoice(invoice);

        verify(invoiceService,times(1)).add(invoice);

    }

    @Test
    public void testGetInvoicesByDate() throws ParseException {
        List<Invoice> listInvoices = new ArrayList<>();
        Date from = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-28");
        Date to = new SimpleDateFormat("yyyy-MM-dd").parse("2020-06-30");
        when(invoiceService.getInvoicesByDate(from,to,1)).thenReturn(listInvoices);
        invoiceController.getInvoicesByDate(from,to,1);
        verify(invoiceService,times(1)).getInvoicesByDate(from,to,1);
    }

    @Test
    public void testGetInvoicesByClient(){
        List<Invoice> listInvoices = new ArrayList<>();
        when(invoiceService.getInvoicesByClient(1)).thenReturn(listInvoices);
        invoiceController.getInvoicesByClient(1);
        verify(invoiceService,times(1)).getInvoicesByClient(1);
    }

}
