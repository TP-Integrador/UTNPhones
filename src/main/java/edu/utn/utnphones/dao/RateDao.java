package edu.utn.utnphones.dao;

import edu.utn.utnphones.domain.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateDao extends JpaRepository<Rate,Integer> {

}
