package com.stathis.cardcostapi.controllers;

import com.stathis.cardcostapi.domain.CardClearing;
import com.stathis.cardcostapi.services.CardClearingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(CardClearingController.BASE_URL)
@AllArgsConstructor
public class CardClearingController {

    public static final String BASE_URL = "api/v1/card-clearings";

    private final CardClearingService cardClearingService;

    @GetMapping
    public List<CardClearing> getAllCardClearings() {
        return cardClearingService.getAllCardClearings();
    }

    @GetMapping("/{id}")
    public CardClearing getCardClearingById(@PathVariable("id") Long id) {
        return cardClearingService.getCardClearingById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CardClearing saveCardClearing(@RequestBody @Valid CardClearing cardClearing) {
        return cardClearingService.saveCardClearing(cardClearing);
    }

    @PutMapping("/{id}")
    public CardClearing updateCardClearing(@PathVariable("id") Long id, @RequestBody @Valid CardClearing cardClearing) {
        return cardClearingService.updateCardClearing(id, cardClearing);
    }

    @DeleteMapping("/{id}")
    public void deleteCardClearingById(@PathVariable("id") Long id) {
        cardClearingService.deleteCardClearingById(id);
    }
}
