package com.stathis.cardcostapi.controllers;

import com.stathis.cardcostapi.model.CardClearingCostRequest;
import com.stathis.cardcostapi.model.CardClearingCostResponse;
import com.stathis.cardcostapi.services.PaymentCardCostService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PaymentCardCostController.BASE_URL)
@AllArgsConstructor
public class PaymentCardCostController {

    public static final String BASE_URL = "api/v1/payment-cards-cost";

    private final PaymentCardCostService paymentCardCostService;

    @PostMapping
    public CardClearingCostResponse calculateCardClearingCost(@RequestBody CardClearingCostRequest cardClearingCostRequest) {
        return paymentCardCostService.calculateCardClearingCost(cardClearingCostRequest);
    }
}
