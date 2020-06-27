package edu.utn.utnphones.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
    @JoinColumn(name = "call_line_id_from")
    private PhoneLine lineIdFrom;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "call_line_id_to")
    private PhoneLine lineIdTo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "call_invoice_id")
    private Invoice invoice;

    @Column(name = "call_minute_price")
    private float minutePrice;

    @Column(name = "call_minute_cost")
    private float minuteCost;

    @NotNull
    @Column(name = "call_duration_seg")
    private int duration;

    @NotNull
    @Column(name = "call_date")
    private LocalDateTime date;
}
