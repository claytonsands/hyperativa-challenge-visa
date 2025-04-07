package org.example.api.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.api.domain.entity.Card;
import org.example.api.domain.repository.CardRepository;
import org.example.api.domain.service.CardService;
import org.example.api.domain.utils.CardNumberUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final CardNumberUtils cardNumberUtils;


    @Override
    public Card register(String cardNumber) throws Exception {
        byte[] encrypted = cardNumberUtils.encrypt(cardNumber);
        String masked = cardNumberUtils.mask(cardNumber);

        Card card = new Card();
        card.setCardEncrypted(encrypted);
        card.setCardMasked(masked);

        return cardRepository.save(card);
    }

    @Override
    public Optional<List<Long>> findCardIdsByCardNumber(String cardNumber) throws Exception {
        byte[] encrypted = cardNumberUtils.encrypt(cardNumber);
        return cardRepository.findCardIdsByCardEncrypted(encrypted);
    }
}
