package org.example.api.controller.v1;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.api.controller.v1.dto.CardLookupRequest;
import org.example.api.controller.v1.dto.CardLookupResponse;
import org.example.api.controller.v1.dto.CardRequest;
import org.example.api.controller.v1.dto.CardResponse;
import org.example.api.domain.service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.example.api.controller.v1.mapper.CardMapper.map;


@Tag(name = "Card")
@RestController
@RequestMapping("/api/v1/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    public ResponseEntity<CardResponse> register(@Valid @RequestBody CardRequest request) throws Exception {
        return new ResponseEntity<>(map(cardService.register(request.cardNumber())), HttpStatus.CREATED);
    }

    @PostMapping("/lookup")
    public ResponseEntity<List<CardLookupResponse>> lookupCard(@Valid @RequestBody CardLookupRequest request) throws Exception {
        return cardService.findCardIdsByCardNumber(request.cardNumber())
                .map(ids -> ids.stream()
                        .map(CardLookupResponse::new)
                        .collect(Collectors.toList()))
                .filter(list -> !list.isEmpty())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}