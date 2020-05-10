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
@Table(name = "phone_lines")
public class PhoneLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "line_id")
    private int id;

    @NotNull
    @Column(name = "line_number")
    private String lineNumber;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "line_status")
    private Status lineStatus;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "line_type_id")
    private LineType lineType;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "line_client_id")
    private Client client;

    @OneToMany(mappedBy = "phone_lines")
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "phone_lines")
    private List<Call> calls;

    public enum Status{
        Active,
        Inactive,
        Suspended
    }
}
