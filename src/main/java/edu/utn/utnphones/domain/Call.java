package edu.utn.utnphones.domain;

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
    @JoinColumn(name = "line_number")
    private PhoneLine lineIdTo;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "line_number")
    private PhoneLine lineIdFrom;

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
