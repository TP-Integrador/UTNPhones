package edu.utn.utnphones.service;

import edu.utn.utnphones.dao.UserDao;
import edu.utn.utnphones.domain.User;
import edu.utn.utnphones.domain.UserType;
import edu.utn.utnphones.dto.ClientDto;
import edu.utn.utnphones.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserService {

    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User login(String username, String password) throws UserNotexistException, ValidationException {
        User us = null;
        if ((username != null) && (password != null)) {

             us = userDao.getByUsername(username, password);
            if (us == null){
                throw new UserNotexistException();
            }
        } else {
            throw new ValidationException("username and password must have a value");
        }
        return us;
    }

    public User getUser(Integer userId) throws UserNotexistException {
       return userDao.findById(userId).orElseThrow(UserNotexistException::new);
    }

    public User getClientById(int idClient) throws ClientNotExistsException {
        User client= userDao.findByIdAndUserType(idClient);
        if(client == null){
            throw new ClientNotExistsException();
        }
        return client;
    }

    public User addClient(User client) throws SQLException, ClientDniAlreadyExists, UserNameAlreadyExists {
        User client1 = null;
        try {
            User user = userDao.findByDni(client.getDNI());
            if (user != null) {
                throw new ClientDniAlreadyExists();
            }
            User user1 = userDao.findByUserName(client.getUsername());
            if ( user1 != null) {
                throw new UserNameAlreadyExists();
            }
            client.setUserType(UserType.builder().Id(1).build());
            return userDao.save(client);
        }catch (Exception e){
            throw new SQLException(e);
        }
    }

    public User updateClient(Integer idClient, ClientDto client) throws ClientNotExistsException, UserNameAlreadyExists, SQLException {
        try {
            User clientAux = userDao.findById(idClient).orElseThrow(ClientNotExistsException::new);
            User client2 = userDao.findByUserName(client.getUsername());
            if ( client2 != null && clientAux.getUserId() != client2.getUserId()) {
                throw new UserNameAlreadyExists();
            }
            clientAux.setName(client.getName());
            clientAux.setLastname(client.getLastname());
            clientAux.setUsername(client.getUsername());
            clientAux.setPassword(client.getPassword());
            clientAux.setCity(client.getCity());
            return userDao.save(clientAux);
        }catch (Exception e){
            throw new SQLException(e);
        }
    }

    public void deleteClient(Integer idClient) throws ClientNotExistsException, ClientRemovedException {
        User clientAux = userDao.findById(idClient).orElseThrow(ClientNotExistsException::new);
        if (clientAux.getActive() == false) {
            throw new ClientRemovedException();
        }
        userDao.updateStatus(false, idClient);
    }
}
