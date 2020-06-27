package edu.utn.utnphones.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDto {
    @JsonProperty
    int code;
    @JsonProperty
    String description;

    public ErrorResponseDto(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
