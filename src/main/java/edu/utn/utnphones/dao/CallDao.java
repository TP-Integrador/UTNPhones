package edu.utn.utnphones.dao;

import edu.utn.utnphones.domain.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface CallDao extends JpaRepository<Call,Integer> {

    @Procedure(value = "sp_insertcall")
    void addCall(String lineFrom, String lineTo, int seg, Date date);

}
