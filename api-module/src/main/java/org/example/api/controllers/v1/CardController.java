package org.example.api.controllers.v1;

import org.example.api.controllers.v1.dto.BatchResponse;
import org.example.api.controllers.v1.mapper.CardMapper;
import org.example.api.domain.service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.api.controllers.v1.dto.CardResponse;
import org.example.api.controllers.v1.dto.CardRequest;

import static org.example.api.controllers.v1.mapper.CardMapper.map;


@RestController
@RequestMapping("/api/v1/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    public ResponseEntity<CardResponse> register(@RequestBody CardRequest request) throws Exception {
        return new ResponseEntity<>(map(cardService.register(request.getCardNumber())), HttpStatus.CREATED);
    }

}