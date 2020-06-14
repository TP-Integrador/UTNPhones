package edu.utn.utnphones.dto;

import javax.validation.constraints.NotNull;

import edu.utn.utnphones.domain.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {

    @NotNull
    private String name;
    @NotNull
    private String lastname;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private City city;

}
