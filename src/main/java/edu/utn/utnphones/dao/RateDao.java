package edu.utn.utnphones.dao;

import edu.utn.utnphones.domain.Rate;
import edu.utn.utnphones.domain.RateCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RateDao extends JpaRepository<Rate, RateCompositeKey> {

    @Modifying
    @Query(value = "insert into rates (city_from_id, city_to_id, rate_value, rate_cost) values (:cityFrom, :cityTo, :value, :cost)", nativeQuery = true)
    void insertRate(@Param("cityFrom") int cityFrom, @Param("cityTo") int cityTo, @Param("value") float value, @Param("cost") float cost);
}
