package org.example.api.domain.service;

import org.example.api.domain.entity.Card;

import java.util.List;
import java.util.Optional;

public interface CardService {
    Card register(String cardNumber) throws Exception;
    Optional<List<Long>> findCardIdsByCardNumber(String cardNumber) throws Exception;
}
