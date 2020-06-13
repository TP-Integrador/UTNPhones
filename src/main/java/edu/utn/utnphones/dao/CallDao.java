package edu.utn.utnphones.dao;

import edu.utn.utnphones.domain.Call;
import edu.utn.utnphones.projections.GetCalls;
import edu.utn.utnphones.projections.MostCalledCities;
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


    @Query(value = "select plo.line_number as OriginNumber, cio.city_name as OriginCity, pld.line_number as DestinationNumber, cid.city_name as DestinationCity, (co.call_minute_price * (co.call_duration_seg/60)) as TotalPrice, co.call_duration_seg as Duration, co.call_date as CallDate\n" +
            "from users uo inner join phone_lines plo on plo.line_user_id = uo.user_id\n" +
            "inner join calls co on co.call_line_id_from = plo.line_id \n" +
            "inner join cities cio on cio.city_id = uo.user_city_id \n" +
            "left outer join phone_lines pld on pld.line_id = co.call_line_id_to\n" +
            "left outer join users ud on ud.user_id = pld.line_user_id \n" +
            "left outer join cities cid on cid.city_id = ud.user_city_id \n" +
            "where uo.user_id = ?1\n",nativeQuery = true)
    List<GetCalls> getCallsByClient(int id);

    @Query(value = "select ci.city_name City, count(ph.line_number) Calls from calls c\n" +
            "inner join phone_lines ph on c.call_line_id_to = ph.line_id\n" +
            "inner join cities ci on ci.city_id = (select city_id from cities where city_prefix = SUBSTRING(ph.line_number,1,LENGTH(ph.line_number)-7))\n" +
            "where c.call_line_id_from in (select line_id from phone_lines where line_user_id = ?1)\n" +
            "group by city_name\n" +
            "order by count(ph.line_number) desc\n" +
            "limit 10",nativeQuery = true)
    List<MostCalledCities> getMostCalledCities(int userId);

}
