package org.example.api.domain.service;

import org.example.api.controller.v1.dto.BatchRequest;
import org.example.api.domain.entity.Batch;

import java.util.List;

public interface BatchService {
    Batch createBatch(BatchRequest request);
    void processBatchCards(Batch batch, List<BatchRequest.CardInput> cards);
}
