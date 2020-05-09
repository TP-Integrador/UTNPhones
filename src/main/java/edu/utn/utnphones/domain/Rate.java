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

    @NotNull //En la base de datos no está como NULL
    private Float value;

    @NotNull //En la base de datos no está como NULL
    private Float cost;
}
