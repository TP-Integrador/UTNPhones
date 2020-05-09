package edu.utn.utnphones.dao;

import edu.utn.utnphones.domain.LinePhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

@Repository
public interface LinePhoneDao extends JpaRepository<LinePhone,Integer> {

}
