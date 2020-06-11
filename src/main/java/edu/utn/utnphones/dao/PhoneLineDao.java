package edu.utn.utnphones.dao;

import edu.utn.utnphones.domain.PhoneLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PhoneLineDao extends JpaRepository<PhoneLine,Integer> {

    @Query(value = "SELECT * FROM phone_lines where line_number = ?1", nativeQuery = true)
    PhoneLine findByNumber(String line);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE phone_lines SET line_status = ?1 where line_id = ?2", nativeQuery = true)
    void updateStatus(String status, int idPhoneLine);
}
