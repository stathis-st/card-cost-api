package com.stathis.cardcostapi.services;

import com.stathis.cardcostapi.domain.CardClearing;

import java.util.List;

public interface CardClearingService {

    List<CardClearing> getAllCardClearings();

    CardClearing getCardClearingById(Long id);

    CardClearing getCardClearingByCountryCode(String countryCode);

    CardClearing saveCardClearing(CardClearing cardClearing);

    CardClearing updateCardClearing(Long id, CardClearing cardClearing);

    void deleteCardClearing(Long id);
}
