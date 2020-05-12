package edu.utn.utnphones.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "rates")
public class Rate {

    @Id
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name = "city_id")
    private City cityFrom;

    @Id
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name = "city_id")
    private City cityTo;

    @Column(name = "rate_value")
    private Float value;

    @Column(name = "rate_cost")
    private Float cost;
}
