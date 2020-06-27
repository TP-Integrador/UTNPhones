package edu.utn.utnphones.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CallDto {

    String lineFrom;
    String lineTo;
    String date;
    Integer seg;
}
