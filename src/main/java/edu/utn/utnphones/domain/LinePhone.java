package edu.utn.utnphones.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "phone_lines")
public class LinePhone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lineNumber;

    // @Enumerated -- ver
    @NotNull
    private int lineStatus;


    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "line_type_id")
    LineType lineType;


    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "line_client_id")
    @JsonBackReference
    Client client;
}
