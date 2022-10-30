package com.stathis.cardcostapi.services;

import com.stathis.cardcostapi.domain.CardClearing;
import com.stathis.cardcostapi.repositories.CardClearingRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CardClearingServiceImpl implements CardClearingService {

    private final CardClearingRepository cardClearingRepository;

    @Override
    public List<CardClearing> getAllCardClearings() {
        return cardClearingRepository.findAll();
    }

    @Override
    public CardClearing getCardClearingById(Long id) {
        return cardClearingRepository.findById(id).orElseThrow(() -> new RuntimeException("Resource with " + id + " not found"));
    }

    @Override
    public CardClearing saveCardClearing(CardClearing cardClearing) {
        return cardClearingRepository.save(cardClearing);
    }

    @Override
    public CardClearing updateCardClearing(Long id, CardClearing cardClearing) {
        CardClearing savedCardClearing;
        try {
            savedCardClearing = getCardClearingById(id);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Resource could not be updated: " + ex.getMessage());
        }

        String countryCode = cardClearing.getCountryCode();
        Double clearingCost = cardClearing.getClearingCost();

        if (Objects.nonNull(countryCode)) {
            savedCardClearing.setCountryCode(cardClearing.getCountryCode());
        }
        if (Objects.nonNull(clearingCost)) {
            savedCardClearing.setClearingCost(cardClearing.getClearingCost());
        }

        return saveCardClearing(savedCardClearing);
    }

    @Override
    public void deleteCardClearing(Long id) {
        try {
            cardClearingRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new RuntimeException("There is no resource to be deleted with id = " + id);
        }
    }
}
