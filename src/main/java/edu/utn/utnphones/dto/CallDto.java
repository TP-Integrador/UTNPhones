package edu.utn.utnphones.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
public class CallDto {

    String lineFrom;
    String lineTo;
    String date;
    Integer seg;
}
