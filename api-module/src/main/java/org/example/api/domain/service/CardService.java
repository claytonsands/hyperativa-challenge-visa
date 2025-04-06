package org.example.api.domain.service;

import org.example.api.domain.entity.Card;

public interface CardService {
    Card register(String cardNumber) throws Exception;
}
