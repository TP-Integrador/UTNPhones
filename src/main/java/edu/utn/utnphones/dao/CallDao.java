package edu.utn.utnphones.dao;

import edu.utn.utnphones.domain.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallDao extends JpaRepository<Call,Integer> {

}
