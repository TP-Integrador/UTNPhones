package edu.utn.utnphones.controller.infra;


import edu.utn.utnphones.config.LoginInfra;
import edu.utn.utnphones.controller.CallController;
import edu.utn.utnphones.controller.PhoneLineController;
import edu.utn.utnphones.domain.PhoneLine;
import edu.utn.utnphones.dto.CallDto;
import edu.utn.utnphones.exception.PhoneLineNotExistException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class AddCallsControllerTest {

    CallController callController;
    PhoneLineController phoneLineController;
    AddCallsController addCallsController;
    LoginInfra loginInfra;
    CallDto callDto;


    @Before
    public void setUp() {
        callController = mock(CallController.class);
        phoneLineController = mock(PhoneLineController.class);
        callDto = mock(CallDto.class);
        loginInfra = mock(LoginInfra.class);
        addCallsController = new AddCallsController(callController,phoneLineController, loginInfra);
    }


    @Test
    public void testAddCallOk() throws PhoneLineNotExistException, ParseException, SQLException {
        String userconfig = "infra";
        String passconfig = "1234";

        when(loginInfra.getUserconfig()).thenReturn(userconfig);
        when(loginInfra.getPassconfig()).thenReturn(passconfig);

        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-05-16 10:32:00");
        CallDto callDto = CallDto.builder().lineFrom("123").lineTo("456").seg(60).date("2020-05-16 10:32:00").build();

        PhoneLine from = PhoneLine.builder().id(1).lineNumber(callDto.getLineFrom()).build();
        PhoneLine to = PhoneLine.builder().id(2).lineNumber(callDto.getLineTo()).build();

        when(phoneLineController.getByNumber("123")).thenReturn(from);
        when(phoneLineController.getByNumber("456")).thenReturn(to);

        ResponseEntity responseEntity = addCallsController.addCall("infra","1234",callDto);

        assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());

    }


    @Test
    public void testAddCallForbidden() throws PhoneLineNotExistException, ParseException, SQLException {
        String userconfig = "infra";
        String passconfig = "1234";

        when(loginInfra.getUserconfig()).thenReturn(userconfig);
        when(loginInfra.getPassconfig()).thenReturn(passconfig);

        CallDto callDto = CallDto.builder().lineFrom("123").lineTo("456").seg(60).date("2020-05-16 10:32:00").build();
        ResponseEntity responseEntity = addCallsController.addCall("infra2","1234",callDto);
        assertEquals(HttpStatus.FORBIDDEN,responseEntity.getStatusCode());

    }

    @Test(expected = PhoneLineNotExistException.class)
    public void testAddCallPhoneLineFromNotExists() throws PhoneLineNotExistException, ParseException, SQLException {
        String userconfig = "infra";
        String passconfig = "1234";

        when(loginInfra.getUserconfig()).thenReturn(userconfig);
        when(loginInfra.getPassconfig()).thenReturn(passconfig);

        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-05-16 10:32:00");
        CallDto callDto = CallDto.builder().lineFrom("123").lineTo("456").seg(60).date("2020-05-16 10:32:00").build();

        PhoneLine from = PhoneLine.builder().id(1).lineNumber(callDto.getLineFrom()).build();
        PhoneLine to = PhoneLine.builder().id(2).lineNumber(callDto.getLineTo()).build();

        when(phoneLineController.getByNumber("123")).thenThrow(new PhoneLineNotExistException("123"));
        when(phoneLineController.getByNumber("456")).thenReturn(to);

        ResponseEntity responseEntity = addCallsController.addCall("infra","1234",callDto);

    }

    @Test(expected = PhoneLineNotExistException.class)
    public void testAddCallPhoneLineToNotExists() throws PhoneLineNotExistException, ParseException, SQLException {
        String userconfig = "infra";
        String passconfig = "1234";

        when(loginInfra.getUserconfig()).thenReturn(userconfig);
        when(loginInfra.getPassconfig()).thenReturn(passconfig);

        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-05-16 10:32:00");
        CallDto callDto = CallDto.builder().lineFrom("123").lineTo("456").seg(60).date("2020-05-16 10:32:00").build();

        PhoneLine from = PhoneLine.builder().id(1).lineNumber(callDto.getLineFrom()).build();
        PhoneLine to = PhoneLine.builder().id(2).lineNumber(callDto.getLineTo()).build();

        when(phoneLineController.getByNumber("123")).thenReturn(from);
        when(phoneLineController.getByNumber("456")).thenThrow(new PhoneLineNotExistException("456"));

        ResponseEntity responseEntity = addCallsController.addCall("infra","1234",callDto);

    }


  }
