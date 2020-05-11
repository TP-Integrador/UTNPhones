package edu.utn.utnphones.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "calls")
public class Call {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "call_id")
    private int Id;


    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "call_line_id_from")   //TODO tiene que ir el nombre de la FK, no el id de la otra tabla, Antes "line_id"
    private PhoneLine lineIdFrom;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "call_line_id_to")   //TODO tiene que ir el nombre de la FK, no el id de la otra tabla, Antes "line_id"
    private PhoneLine lineIdTo;


    @NotNull
    @Column(name = "call_minute_price")
    private float minutePrice;

    @NotNull
    @Column(name = "call_duration_seg")
    private int duration;

    @NotNull
    @Column(name = "call_date")
    private Date date;

    @Column(name = "call_invoice_id")
    private int invoiceId;
}
