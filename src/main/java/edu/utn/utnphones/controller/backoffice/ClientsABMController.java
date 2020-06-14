package edu.utn.utnphones.controller.backoffice;

import edu.utn.utnphones.controller.UserController;
import edu.utn.utnphones.domain.PhoneLine;
import edu.utn.utnphones.domain.User;
import edu.utn.utnphones.dto.ClientDTO;
import edu.utn.utnphones.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.jws.soap.SOAPBinding;
import java.net.URI;
import java.sql.SQLException;

@RestController
@RequestMapping("backoffice/client")
public class ClientsABMController {

    private UserController userController;

    @Autowired
    public ClientsABMController(UserController userController) {
        this.userController = userController;
    }

    //TODO CRUD: Falta Update and Delete. Agregar campo en tabla para estado.

    @GetMapping("/{idClient}")
    public ResponseEntity<User> getClient(@PathVariable int idClient) throws ClientNotExistsException {
        ResponseEntity<User> responseEntity = null;
        User client = userController.getClientById(idClient);
        return ResponseEntity.ok().body(client);
    }

    @PostMapping
    public ResponseEntity<User> addClient(@RequestBody User client) throws UserNameAlreadyExists, SQLException, ClientDniAlreadyExists {
        userController.addClient(client);
        return ResponseEntity.created(getLocation(client)).build();
    }


    @PutMapping("/{idClient}")
    public ResponseEntity<User> updateClient(@PathVariable int idClient, @RequestBody ClientDTO client) throws ClientNotExistsException, UserNameAlreadyExists ,SQLException{
        userController.updateClient(idClient,client);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{idClient}")
    public ResponseEntity<User> deleteClient(@PathVariable int idClient) throws ClientNotExistsException, ClientRemovedException {
        userController.deleteClient(idClient);
        return ResponseEntity.ok().build();
    }

    public URI getLocation(User client) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(client.getUserId())
                .toUri();
    }
}
