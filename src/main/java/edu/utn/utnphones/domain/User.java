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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_name")
    @NotNull
    private String name;

    @Column(name = "user_lastname")
    @NotNull
    private String lastname;

    @Column(name = "user_dni")
    @NotNull
    private String DNI;

    @Column(name = "username")
    @NotNull
    private String username;

    @Column(name = "userpass")
    @NotNull
    private String password;

    @Column(name = "user_active")
    private Boolean active;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_city_id")
    private City city;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_type_id")
    private UserType userType;

    @OneToMany(mappedBy = "user")
    @JsonBackReference(value = "linePhones")
    private List<PhoneLine> linePhones;

}

