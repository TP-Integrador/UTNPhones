package edu.utn.utnphones.dao;

import edu.utn.utnphones.domain.Call;
import edu.utn.utnphones.projections.GetCalls;
import edu.utn.utnphones.projections.MostCalledCities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CallDao extends JpaRepository<Call, Integer> {

    @Query(value = "select plo.line_number as OriginNumber, cio.city_name as OriginCity, pld.line_number as DestinationNumber, cid.city_name as DestinationCity, (co.call_minute_price * (co.call_duration_seg/60)) as TotalPrice, co.call_duration_seg as Duration,co.call_date as CallDate\n" +
            "from calls co inner join phone_lines plo on plo.line_id = co.call_line_id_from\n" +
            "inner join cities cio on cio.city_id = (select getcity(plo.line_number))\n" +
            "inner join phone_lines pld on pld.line_id = co.call_line_id_to\n" +
            "inner join cities cid on cid.city_id = (select getcity(pld.line_number))\n" +
            "inner join users u on u.user_id = plo.line_user_id\n" +
            "where (co.call_date between ?1 and ?2) and u.user_id = ?3\n" +
            "order by co.call_date;", nativeQuery = true)
    List<GetCalls> getCallsByDate(Date dateFrom, Date dateTo, int userId);

    @Query(value = "select plo.line_number as OriginNumber, cio.city_name as OriginCity, pld.line_number as DestinationNumber, cid.city_name as DestinationCity, (co.call_minute_price * (co.call_duration_seg/60)) as TotalPrice, co.call_duration_seg as Duration, co.call_date as CallDate\n" +
            "from calls co inner join phone_lines plo on plo.line_id = co.call_line_id_from\n" +
            "inner join cities cio on cio.city_id = (select getcity(plo.line_number))\n" +
            "inner join phone_lines pld on pld.line_id = co.call_line_id_to\n" +
            "inner join cities cid on cid.city_id = (select getcity(pld.line_number))\n" +
            "inner join users u on u.user_id = plo.line_user_id\n" +
            "where u.user_id = ?1\n" +
            "order by co.call_date;", nativeQuery = true)
    List<GetCalls> getCallsByClient(int id);

    @Query(value = "select ci.city_name City, count(ph.line_number) Calls from calls c\n" +
            "inner join phone_lines ph on c.call_line_id_to = ph.line_id\n" +
            "inner join cities ci on ci.city_id = (select getcity(ph.line_number))\n" +
            "where c.call_line_id_from in (select line_id from phone_lines where line_user_id = ?1)\n" +
            "group by city_name\n" +
            "order by count(ph.line_number) desc\n" +
            "limit 10", nativeQuery = true)
    List<MostCalledCities> getMostCalledCities(int userId);
}
