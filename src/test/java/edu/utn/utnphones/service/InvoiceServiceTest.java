package edu.utn.utnphones.service;

import edu.utn.utnphones.dao.InvoiceDao;
import edu.utn.utnphones.domain.Invoice;
import edu.utn.utnphones.exception.ResourcesNotExistException;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InvoiceServiceTest {
    private InvoiceDao invoiceDao;
    private InvoiceService invoiceService;

    @Before
    public void setUp(){
        invoiceDao = mock(InvoiceDao.class);
        invoiceService = new InvoiceService(invoiceDao);
    }

    @Test
    public void testGetAll(){
        List<Invoice> listInvoices = new ArrayList<>();
        when(invoiceService.getAll()).thenReturn(listInvoices);

        assertEquals(listInvoices,listInvoices);
    }

    @Test
    public void testAddOk() throws SQLException {
        Invoice invoice = Invoice.builder().id(1).build();
        when(invoiceDao.save(invoice)).thenReturn(invoice);
        invoiceService.add(invoice);
    }

    @Test
    public void testGetByIdOk() throws ResourcesNotExistException {
        Optional<Invoice> invoices = Optional.ofNullable(Invoice.builder().id(1).build());
        when(invoiceDao.findById(1)).thenReturn(invoices);

        assertEquals(invoices,Optional.of(invoiceService.getById(1)));
    }


    @Test(expected = ResourcesNotExistException.class)
    public void testGetByIdNotExists() throws ResourcesNotExistException {
        Invoice invoice = null;
        when(invoiceDao.findById(1)).thenReturn(Optional.ofNullable(invoice));
        invoiceService.getById(1);
    }

    @Test
    public void testGetInvoiceByDate() throws ParseException {
        List<Invoice> listInvoices = new ArrayList<>();
        Date from = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-28");
        Date to = new SimpleDateFormat("yyyy-MM-dd").parse("2020-06-30");
        when(invoiceDao.getInvoicesByDate(from,to,1)).thenReturn(listInvoices);

        assertEquals(listInvoices,invoiceService.getInvoicesByDate(from,to,1));
    }

    @Test
    public void testGetInvoiceByClient(){
        List<Invoice> listInvoices = new ArrayList<>();
        when(invoiceDao.getInvoicesByClient(1)).thenReturn(listInvoices);

        assertEquals(listInvoices,invoiceService.getInvoicesByClient(1));
    }
}
