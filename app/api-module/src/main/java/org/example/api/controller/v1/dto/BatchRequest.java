package org.example.api.controller.v1.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BatchRequest {
    private String name;
    private String batchCode;
    private LocalDate batchDate;
    private List<CardInput> cards;

    @Data
    public static class CardInput {
        private String cardNumber;
        private String lineIdentifier;
        private int orderInBatch;
    }
}
