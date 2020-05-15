package edu.utn.utnphones.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "rates")
@IdClass(value = RateCompositeKey.class)
public class Rate{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_from_id")
    private City cityFrom;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_to_id")
    private City cityTo;

    @Column(name = "rate_value")
    private Float value;

    @Column(name = "rate_cost")
    private Float cost;

}
