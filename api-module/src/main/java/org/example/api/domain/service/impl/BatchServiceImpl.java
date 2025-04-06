package org.example.api.domain.service.impl;

import org.example.api.controllers.v1.dto.BatchRequest;
import org.example.api.domain.entity.Batch;
import org.example.api.domain.entity.Card;
import org.example.api.domain.repository.BatchRepository;
import org.example.api.domain.repository.CardRepository;
import org.example.api.domain.service.BatchService;
import org.example.api.domain.utils.CardNumberUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

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
                Card card = new Card();
                card.setBatch(batch);
                card.setCardEncrypted(cardNumberUtils.encrypt(cardInput.getCardNumber()));
                card.setCardMasked(cardNumberUtils.mask(cardInput.getCardNumber()));
                card.setLineIdentifier(cardInput.getLineIdentifier());
                card.setOrderInBatch(cardInput.getOrderInBatch());

                cardRepository.save(card);
            } catch (Exception e) {
                System.err.println("Erro ao registrar cartão no lote " + batch.getId() + ": " + e.getMessage());
            }
        }
    }

}
