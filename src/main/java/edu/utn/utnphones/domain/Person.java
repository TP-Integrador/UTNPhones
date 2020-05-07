package edu.utn.utnphones.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Person {

    private Integer id;

    private String name;

    private String lastname;

    private String DNI;

    private String username;

    private String password;
}

