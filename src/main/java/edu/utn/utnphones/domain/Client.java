package edu.utn.utnphones.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Integer id;

    @NotNull
    @Column(name = "client_name")
    private String name;

    @NotNull
    @Column(name = "client_lastname")
    private String lastname;

    @NotNull
    @Column(name = "client_dni")
    private String DNI;

    @NotNull
    @Column(name = "client_username")
    private String username;

    @NotNull
    @Column(name = "client_password")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_city_id")
    City city;

    @OneToMany(mappedBy = "client")
    private List<LinePhone> linePhones;

}
