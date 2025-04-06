package org.example.api.controller.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CardRequest {
    @JsonProperty("card_number")
    @Size(max = 16)
    private String cardNumber;
}
