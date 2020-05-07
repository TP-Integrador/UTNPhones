package edu.utn.utnphones.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Rate {

    @NotNull
    private Integer cityFrom;

    @NotNull
    private Integer cityTo;

    @NotNull //En la base de datos no está como NULL
    private Float value;
}
