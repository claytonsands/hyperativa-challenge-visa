package org.example.api.controller.v1.mapper;

import org.example.api.controller.v1.dto.BatchResponse;
import org.example.api.domain.entity.Batch;

public class BatchMapper {
    public static BatchResponse map(Batch batch){
        BatchResponse response = new BatchResponse();
        response.setId(batch.getId().toString());
        response.setCode(batch.getBatchCode());
        response.setName(batch.getName());
        return response;
    }
}
