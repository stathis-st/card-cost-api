package com.stathis.cardcostapi.services;

import com.stathis.cardcostapi.domain.CardClearing;
import com.stathis.cardcostapi.exception.ResourceConstraintViolationException;
import com.stathis.cardcostapi.exception.ResourceNotDeletedException;
import com.stathis.cardcostapi.exception.ResourceNotFoundException;
import com.stathis.cardcostapi.exception.ResourceNotUpdatedException;
import com.stathis.cardcostapi.repositories.CardClearingRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.stathis.cardcostapi.exception.ResourceConstraintViolationException.SAVE_RESOURCE_CONSTRAINT_VIOLATION;
import static com.stathis.cardcostapi.exception.ResourceNotDeletedException.RESOURCE_COULD_NOT_BE_DELETED;
import static com.stathis.cardcostapi.exception.ResourceNotFoundException.RESOURCE_NOT_FOUND_WITH_ID;
import static com.stathis.cardcostapi.exception.ResourceNotUpdatedException.RESOURCE_COULD_NOT_BE_UPDATED;

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
        return cardClearingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND_WITH_ID + id));
    }

    @Override
    public CardClearing saveCardClearing(CardClearing cardClearing) {
        try {
            return cardClearingRepository.save(cardClearing);
        } catch (DataIntegrityViolationException ex) {
            throw new ResourceConstraintViolationException(SAVE_RESOURCE_CONSTRAINT_VIOLATION);
        }
    }

    @Override
    public CardClearing updateCardClearing(Long id, CardClearing cardClearing) {
        CardClearing savedCardClearing;
        try {
            savedCardClearing = getCardClearingById(id);
        } catch (ResourceNotFoundException ex) {
            throw new ResourceNotUpdatedException(RESOURCE_COULD_NOT_BE_UPDATED + ex.getMessage());
        }

        String countryCode = cardClearing.getCountryCode();
        Double clearingCost = cardClearing.getClearingCost();

        if (Objects.nonNull(countryCode)) {
            savedCardClearing.setCountryCode(cardClearing.getCountryCode());
        }
        if (Objects.nonNull(clearingCost)) {
            savedCardClearing.setClearingCost(cardClearing.getClearingCost());
        }

        try {
            return saveCardClearing(savedCardClearing);
        } catch (ResourceConstraintViolationException ex) {
            throw new ResourceNotUpdatedException(RESOURCE_COULD_NOT_BE_UPDATED + ex.getMessage());
        }
    }

    @Override
    public void deleteCardClearing(Long id) {
        try {
            cardClearingRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResourceNotDeletedException(RESOURCE_COULD_NOT_BE_DELETED + id);
        }
    }
}
