package edu.utn.utnphones.controller;

import edu.utn.utnphones.domain.User;
import edu.utn.utnphones.dto.ClientDto;
import edu.utn.utnphones.exception.*;
import edu.utn.utnphones.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.sql.SQLException;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public User login(String username, String password) throws UserNotexistException, ValidationException {
        if ((username != null) && (password != null)) {
            return userService.login(username, password);
        } else {
            throw new ValidationException("username and password must have a value");
        }
    }

    public User getUserById(Integer userId) throws UserNotexistException {
        return userService.getUser(userId);
    }


    public User getClientById(int idClient) throws ClientNotExistsException {
        return userService.getClientById(idClient);
    }


    public User addClient(User client) throws UserNameAlreadyExists, SQLException, ClientDniAlreadyExists {
        return userService.addClient(client);
    }

    public User updateClient(Integer idClient, ClientDto client) throws ClientNotExistsException, SQLException, UserNameAlreadyExists {
        return userService.updateClient(idClient, client);
    }

    public void deleteClient(Integer idClient) throws ClientNotExistsException, ClientRemovedException {
        userService.deleteClient(idClient);
    }

}
