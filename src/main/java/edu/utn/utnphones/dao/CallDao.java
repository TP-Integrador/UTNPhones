package edu.utn.utnphones.dao;

import edu.utn.utnphones.domain.Call;
import edu.utn.utnphones.projections.CallCant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CallDao extends JpaRepository<Call,Integer> {

    @Query(value = "" , nativeQuery = true)
    List<CallCant> getCallCant();
}
