package org.example.api.controller.v1.mapper;

import org.example.api.controller.v1.dto.CardResponse;
import org.example.api.domain.entity.Card;

public class CardMapper {

    public static CardResponse map(Card card){
        CardResponse response = new CardResponse();
        response.setId(card.getId());
        response.setCardNumber(card.getCardMasked());
        return response;
    }

}
