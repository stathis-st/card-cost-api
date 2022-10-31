package com.stathis.cardcostapi.services;

import com.stathis.cardcostapi.model.CardClearingCostRequest;
import com.stathis.cardcostapi.model.CardClearingCostResponse;

public interface PaymentCardCostService {

    CardClearingCostResponse calculateCardClearingCost(CardClearingCostRequest cardClearingCostRequest);
}
