package edu.utn.utnphones.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "rates")
public class Rate {

    @NotNull
    private Integer cityFrom;

    @NotNull
    private Integer cityTo;

    @NotNull //En la base de datos no está como NULL
    private Float value;
}
