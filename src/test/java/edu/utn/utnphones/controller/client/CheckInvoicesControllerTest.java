package edu.utn.utnphones.controller.client;

import edu.utn.utnphones.controller.InvoiceController;
import edu.utn.utnphones.controller.client.CheckInvoicesController;
import edu.utn.utnphones.domain.Invoice;
import edu.utn.utnphones.domain.User;
import edu.utn.utnphones.domain.UserType;
import edu.utn.utnphones.exception.UserNotexistException;
import edu.utn.utnphones.session.SessionManager;
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
import static org.mockito.Mockito.*;

public class CheckInvoicesControllerTest {
    InvoiceController invoiceController;
    CheckInvoicesController checkInvoicesController;
    SessionManager sessionManager;
    Invoice invoice;

    @Before
    public void setUp(){
        invoiceController = mock(InvoiceController.class);
        sessionManager = mock(SessionManager.class);
        invoice = mock(Invoice.class);
        checkInvoicesController = new CheckInvoicesController(invoiceController,sessionManager);

    }

    @Test
    public void getInvoicesByDateOk() throws ParseException, UserNotexistException {
        User user = User.builder().userId(1).build();
        user.setUserType(UserType.builder().type("Client").build());
        List<Invoice> invoiceList = new ArrayList<>();
        Date from = new SimpleDateFormat("yyyy/MM/dd").parse("2020/05/28");
        Date to = new SimpleDateFormat("yyyy/MM/dd").parse("2020/06/30");
        when(sessionManager.getCurrentUser("token")).thenReturn(user);
        invoiceList.add(invoice);
        when(invoiceController.getInvoicesByDate(from,to,1)).thenReturn(invoiceList);

        ResponseEntity<List<Invoice>> responseEntity = checkInvoicesController.getInvoicesByDate("token","2020/05/28","2020/06/30");

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(invoiceList,responseEntity.getBody());
    }

    @Test
    public void getInvoicesByDateNoContent() throws UserNotexistException, ParseException {
        User user = User.builder().userId(1).build();
        user.setUserType(UserType.builder().type("Client").build());
        List<Invoice> invoiceList = Collections.emptyList();
        Date from = new Date();
        Date to = new Date(2) ;
        when(sessionManager.getCurrentUser("token")).thenReturn(user);

        when(invoiceController.getInvoicesByDate(from,to,1)).thenReturn(invoiceList);

        ResponseEntity<List<Invoice>> responseEntity = checkInvoicesController.getInvoicesByDate("token","2020/05/28","2020/06/30");

        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
    }

    @Test
    public void getInvoicesByDateNull() throws UserNotexistException, ParseException {
        User user = User.builder().userId(1).build();
        user.setUserType(UserType.builder().type("Client").build());
        List<Invoice> invoicesList = new ArrayList<>();
        when(sessionManager.getCurrentUser("token")).thenReturn(user);
        when(invoiceController.getInvoicesByDate(null,null,1)).thenReturn(invoicesList);

        ResponseEntity<List<Invoice>> responseEntity = checkInvoicesController.getInvoicesByDate("token",null,null);
        assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());
    }

    @Test
    public void getInvoicesByDateForbidden() throws UserNotexistException, ParseException {
        User user = User.builder().userId(1).build();
        user.setUserType(UserType.builder().type("Employee").build());
        when(sessionManager.getCurrentUser("token")).thenReturn(user);

        ResponseEntity<List<Invoice>> responseEntity = checkInvoicesController.getInvoicesByDate("token",null,null);
        assertEquals(HttpStatus.FORBIDDEN,responseEntity.getStatusCode());

    }
}
