package org.example.api.controllers.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BatchResponse {
    @JsonProperty("batch_id")
    private String id;
    @JsonProperty("batch_name")
    private String name;
    @JsonProperty("batch_code")
    private String code;
    @JsonProperty("total_records")
    private Integer totalRecords;
}
