package edu.utn.utnphones.controller.infra;


import edu.utn.utnphones.controller.CallController;
import edu.utn.utnphones.controller.PhoneLineController;
import edu.utn.utnphones.domain.Call;
import edu.utn.utnphones.domain.PhoneLine;
import edu.utn.utnphones.dto.CallDto;
import edu.utn.utnphones.exception.PhoneLineNotExistException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class AddCallsControllerTest {

    CallController callController;
    PhoneLineController phoneLineController;
    AddCallsController addCallsController;
    CallDto callDto;


    @Before
    public void setUp() {
        // para mockear el getLocation() {
        HttpServletRequest mockRequest = new MockHttpServletRequest();
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);
        // }
        callController = mock(CallController.class);
        phoneLineController = mock(PhoneLineController.class);
        callDto = mock(CallDto.class);
        addCallsController = new AddCallsController(callController,phoneLineController);
    }

    @After
    public void teardown() {
        RequestContextHolder.resetRequestAttributes();
    }
    /*
    @PostMapping
    public ResponseEntity addCall(@RequestBody @Valid CallDto callDto) throws PhoneLineNotExistException {
    PhoneLine from = phoneLineController.getByNumber(callDto.getLineFrom());
    PhoneLine to = phoneLineController.getByNumber(callDto.getLineTo());

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime dateTime = LocalDateTime.parse(callDto.getDate(),formatter);

    Call call = Call.builder().lineIdFrom(from).lineIdTo(to).duration(callDto.getSeg()).date(dateTime).build();
    Call newCall = callController.addCall(call);
    return ResponseEntity.created(getLocation(newCall)).build();
  }
     */

    @Test
    public void testAddCallOk() throws PhoneLineNotExistException, DateTimeException {

        CallDto callDto = CallDto.builder().lineFrom("123").lineTo("456").seg(60).date("2020-05-16 10:32:00").build();
        PhoneLine from = PhoneLine.builder().id(1).lineNumber(callDto.getLineFrom()).build();
        PhoneLine to = PhoneLine.builder().id(2).lineNumber(callDto.getLineTo()).build();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(callDto.getDate(),formatter);

        when(phoneLineController.getByNumber("123")).thenReturn(from);
        when(phoneLineController.getByNumber("456")).thenReturn(to);

        Call call = Call.builder().lineIdFrom(from).lineIdTo(to).duration(callDto.getSeg()).date(dateTime).build();
        when(callController.addCall(call)).thenReturn(call);


        ResponseEntity<Call> responseEntity = addCallsController.addCall(callDto);
        assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
    }


    @Test(expected = PhoneLineNotExistException.class)
    public void testAddCallPhoneLineFromNotExists() throws PhoneLineNotExistException, DateTimeException {

        CallDto callDto = CallDto.builder().lineFrom("123").lineTo("456").seg(60).date("2020-05-16 10:32:00").build();
        PhoneLine from = PhoneLine.builder().id(1).lineNumber(callDto.getLineFrom()).build();
        PhoneLine to = PhoneLine.builder().id(2).lineNumber(callDto.getLineTo()).build();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(callDto.getDate(),formatter);

        when(phoneLineController.getByNumber("123")).thenThrow(new PhoneLineNotExistException("123"));
        when(phoneLineController.getByNumber("456")).thenReturn(to);

        Call call = Call.builder().lineIdFrom(from).lineIdTo(to).duration(callDto.getSeg()).date(dateTime).build();
        when(callController.addCall(call)).thenReturn(call);
        ResponseEntity<Call> responseEntity = addCallsController.addCall(callDto);


    }

    @Test(expected = DateTimeException.class)
    public void testAddCallPhoneLineToNotExists() throws PhoneLineNotExistException, DateTimeException {
        CallDto callDto = CallDto.builder().lineFrom("123").lineTo("456").seg(60).date("2020-05-16 10:32:").build();
        PhoneLine from = PhoneLine.builder().id(1).lineNumber(callDto.getLineFrom()).build();
        PhoneLine to = PhoneLine.builder().id(2).lineNumber(callDto.getLineTo()).build();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(callDto.getDate(),formatter);

        when(phoneLineController.getByNumber("123")).thenReturn(from);
        when(phoneLineController.getByNumber("456")).thenReturn(to);

        Call call = Call.builder().lineIdFrom(from).lineIdTo(to).duration(callDto.getSeg()).date(dateTime).build();
        when(callController.addCall(call)).thenReturn(call);
        ResponseEntity<Call> responseEntity = addCallsController.addCall(callDto);

    }


  }
