package edu.utn.utnphones.controller.backoffice;

import edu.utn.utnphones.controller.InvoiceController;
import edu.utn.utnphones.domain.Invoice;
import edu.utn.utnphones.exception.UserNotexistException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CheckInvoicesClientControllerTest {

    private CheckInvoicesClientController checkInvoicesClientController;
    private InvoiceController invoiceController;
    private Invoice invoice;

    @Before
    public void setUp(){
        invoiceController = mock(InvoiceController.class);
        invoice = mock(Invoice.class);
        checkInvoicesClientController = new CheckInvoicesClientController(invoiceController);
    }

    @Test
    public void testGetInvoicesByClientDateOk() throws UserNotexistException, ParseException {
        List<Invoice> listInvoices = new ArrayList<>();
        listInvoices.add(invoice);
        when(invoiceController.getInvoicesByClient(1)).thenReturn(listInvoices);

        ResponseEntity<List<Invoice>> responseEntity = checkInvoicesClientController.getInvoices(null,null,1);

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(listInvoices,responseEntity.getBody());
    }

    @Test
    public void testGetInvoicesByClientDateNoContent() throws UserNotexistException, ParseException {
        List<Invoice> listInvoices = Collections.emptyList();
        when(invoiceController.getInvoicesByClient(1)).thenReturn(listInvoices);

        ResponseEntity<List<Invoice>> responseEntity = checkInvoicesClientController.getInvoices(null,null,1);

        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
    }

    @Test
    public void testGetInvoicesByDateOk() throws ParseException, UserNotexistException {
        List<Invoice> invoiceList = new ArrayList<>();
        Date from = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-28");
        Date to = new SimpleDateFormat("yyyy-MM-dd").parse("2020-06-30");
        invoiceList.add(invoice);
        when(invoiceController.getInvoicesByDate(from,to,1)).thenReturn(invoiceList);

        ResponseEntity<List<Invoice>> responseEntity = checkInvoicesClientController.getInvoices("2020-05-28","2020-06-30",1);

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(invoiceList,responseEntity.getBody());
    }

    @Test
    public void testGetInvoicesByDateNoContent() throws ParseException, UserNotexistException {
        List<Invoice> invoiceList = Collections.emptyList();
        Date from = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-28");
        Date to = new SimpleDateFormat("yyyy-MM-dd").parse("2020-06-30");
        when(invoiceController.getInvoicesByDate(from,to,1)).thenReturn(invoiceList);

        ResponseEntity<List<Invoice>> responseEntity = checkInvoicesClientController.getInvoices("2020-05-28","2020-06-30",1);

        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
    }

    @Test
    public void testGetInvoicesByDateNullFrom() throws ParseException, UserNotexistException {
        List<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(invoice);
        Date to = new SimpleDateFormat("yyyy-MM-dd").parse("2020-06-30");
        when(invoiceController.getInvoicesByDate(null,to,1)).thenReturn(invoiceList);

        ResponseEntity<List<Invoice>> responseEntity = checkInvoicesClientController.getInvoices(null,"2020-06-30",1);

        assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());
    }

    @Test
    public void testGetInvoicesByDateNullTo() throws ParseException, UserNotexistException {
        List<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(invoice);
        Date from = new SimpleDateFormat("yyyy-MM-dd").parse("2020-06-30");
        when(invoiceController.getInvoicesByDate(from,null,1)).thenReturn(invoiceList);

        ResponseEntity<List<Invoice>> responseEntity = checkInvoicesClientController.getInvoices("2020-05-28",null,1);

        assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());
    }
}
