package edu.utn.utnphones.dao;

import edu.utn.utnphones.domain.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CallDao extends JpaRepository<Call,Integer> {

    @Procedure(value = "sp_insertcall")
    void addCall(String lineFrom, String lineTo, int seg, Date date);

    @Query(value = "select * from calls c inner join phone_lines pl on c.call_line_id_from = pl.line_id \n" +
            "inner join users u on pl.line_user_id = u.user_id \n" +
            "where c.call_date >= ?1  and c.call_date <= ?2 and u.user_id = ?3",nativeQuery = true)
    List<Call> getCallsByDate(Date dateFrom, Date dateTo, int userId);


}
