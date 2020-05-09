package edu.utn.utnphones.service;

import edu.utn.utnphones.dao.ClientDao;
import edu.utn.utnphones.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private ClientDao clientDao;

    @Autowired
    public ClientService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public List<Client> getAll(){
        return clientDao.findAll();
    }

    public void add(Client client){
        clientDao.save(client);
    }
}
