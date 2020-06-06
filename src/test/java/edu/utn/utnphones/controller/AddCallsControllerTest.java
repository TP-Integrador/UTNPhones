package edu.utn.utnphones.controller;

import edu.utn.utnphones.controller.web.AddCallsController;
import edu.utn.utnphones.dto.CallDto;
import org.junit.Before;

import static org.mockito.Mockito.mock;

public class AddCallsControllerTest {

    CallController callController;
    AddCallsController addCallsController;
    CallDto callDto;

    @Before
    public void setUp() {
        callController = mock(CallController.class);
        callDto = mock(CallDto.class);
        addCallsController = new AddCallsController(callController);
    }


}
