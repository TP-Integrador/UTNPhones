package edu.utn.utnphones.controller.infra;


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
import static org.mockito.Mockito.*;


public class AddCallsControllerTest {
    //TODO hacer Test

    CallController callController;
    PhoneLineController phoneLineController;
    AddCallsController addCallsController;
    CallDto callDto;
    String userconfig;
    String passconfig;


    @Before
    public void setUp() {
        callController = mock(CallController.class);
        phoneLineController = mock(PhoneLineController.class);
        callDto = mock(CallDto.class);
        userconfig = "infra";
        passconfig = "1234";
        addCallsController = new AddCallsController(callController,phoneLineController);
    }


    @Test
    public void testAddCallOk() throws PhoneLineNotExistException, ParseException, SQLException {

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
    public void testAddCallForbidder() throws PhoneLineNotExistException, ParseException, SQLException {

        CallDto callDto = CallDto.builder().lineFrom("123").lineTo("456").seg(60).date("2020-05-16 10:32:00").build();
        ResponseEntity responseEntity = addCallsController.addCall("infra2","1234",callDto);
        assertEquals(HttpStatus.FORBIDDEN,responseEntity.getStatusCode());

    }


}
