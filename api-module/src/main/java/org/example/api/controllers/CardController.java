package org.example.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardController {

    @GetMapping("/api/v1/cards")
    public String getCards() { return "CARDS"; }

}
