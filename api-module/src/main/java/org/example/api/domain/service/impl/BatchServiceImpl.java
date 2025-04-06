package org.example.api.domain.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.api.controller.v1.dto.BatchRequest;
import org.example.api.domain.entity.Batch;
import org.example.api.domain.entity.Card;
import org.example.api.domain.repository.BatchRepository;
import org.example.api.domain.repository.CardRepository;
import org.example.api.domain.service.BatchService;
import org.example.api.domain.utils.CardNumberUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class BatchServiceImpl implements BatchService {

    private final BatchRepository batchRepository;
    private final CardRepository cardRepository;
    private final CardNumberUtils cardNumberUtils;

    public BatchServiceImpl(BatchRepository batchRepository, CardRepository cardRepository, CardNumberUtils cardNumberUtils) {
        this.batchRepository = batchRepository;
        this.cardRepository = cardRepository;
        this.cardNumberUtils = cardNumberUtils;
    }

    @Override
    public Batch createBatch(BatchRequest request) {
        Batch batch = new Batch();
        batch.setName(request.getName());
        batch.setBatchCode(request.getBatchCode());
        batch.setBatchDate(request.getBatchDate());
        return batchRepository.save(batch);
    }

    @Async
    public void processBatchCards(Batch batch, List<BatchRequest.CardInput> cards) {
        for (BatchRequest.CardInput cardInput : cards) {
            try {
                Card card = createCard(cardInput, batch);
                cardRepository.save(card);
            } catch (Exception e) {
                log.error("Error processing card line {} from batch_id {}", cardInput.getLineIdentifier()+cardInput.getOrderInBatch(), batch.getId());
            }
        }
    }

    private Card createCard(BatchRequest.CardInput cardInput, Batch batch) throws Exception {
        Objects.requireNonNull(cardInput, "Card input cannot be null");
        Objects.requireNonNull(batch, "Batch cannot be null");

        return Card.builder()
                .batch(batch)
                .cardEncrypted(cardNumberUtils.encrypt(cardInput.getCardNumber()))
                .cardMasked(cardNumberUtils.mask(cardInput.getCardNumber()))
                .lineIdentifier(cardInput.getLineIdentifier())
                .orderInBatch(cardInput.getOrderInBatch())
                .createdAt(LocalDateTime.now())
                .build();
    }

}
