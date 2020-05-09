package edu.utn.utnphones.controller;

import edu.utn.utnphones.domain.Client;
import edu.utn.utnphones.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("clients")
public class ClientController {

    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    public List<Client> getClients(){
        return clientService.getAll();
    }

    @PostMapping
    public void addClient(@RequestBody @Valid Client client){
        clientService.add(client);
    }
}
