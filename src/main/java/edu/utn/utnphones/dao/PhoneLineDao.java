package edu.utn.utnphones.dao;

import edu.utn.utnphones.domain.PhoneLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

@Repository
public interface PhoneLineDao extends JpaRepository<PhoneLine,Integer> {

    @Query(value = "SELECT * from phone_lines  where line_number = ?1", nativeQuery = true)
    PhoneLine findByNumber(String line);
}
