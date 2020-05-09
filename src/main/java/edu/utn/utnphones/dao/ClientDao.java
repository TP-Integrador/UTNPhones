package edu.utn.utnphones.dao;

import edu.utn.utnphones.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDao extends JpaRepository<Client,Integer> {

}
