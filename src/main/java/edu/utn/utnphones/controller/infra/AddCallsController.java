package edu.utn.utnphones.controller.infra;

import edu.utn.utnphones.controller.CallController;
import edu.utn.utnphones.controller.PhoneLineController;
import edu.utn.utnphones.domain.Call;
import edu.utn.utnphones.domain.PhoneLine;
import edu.utn.utnphones.dto.CallDto;
import edu.utn.utnphones.exception.PhoneLineNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("infra/calls")
public class AddCallsController {

    private CallController callController;
    private PhoneLineController phoneLineController;

    @Autowired
    public AddCallsController(CallController callController, PhoneLineController phoneLineController) {
        this.callController = callController;
        this.phoneLineController = phoneLineController;
    }

    @PostMapping
    public ResponseEntity<Call> addCall(@RequestBody CallDto callDto) throws PhoneLineNotExistException, DateTimeException {
        PhoneLine from = phoneLineController.getByNumber(callDto.getLineFrom());
        PhoneLine to = phoneLineController.getByNumber(callDto.getLineTo());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(callDto.getDate(),formatter);

        Call call = Call.builder().lineIdFrom(from).lineIdTo(to).duration(callDto.getSeg()).date(dateTime).build();
        Call addCall = callController.addCall(call);
        return ResponseEntity.created(getLocation(addCall)).build();
    }

    public URI getLocation(Call addCall) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(addCall.getId())
                .toUri();
    }
}
