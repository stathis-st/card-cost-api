package com.stathis.cardcostapi.services;

import com.stathis.cardcostapi.domain.CardClearing;
import com.stathis.cardcostapi.exception.ResourceNotDeletedException;
import com.stathis.cardcostapi.exception.ResourceNotFoundException;
import com.stathis.cardcostapi.exception.ResourceNotUpdatedException;
import com.stathis.cardcostapi.repositories.CardClearingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.stathis.cardcostapi.exception.ResourceNotDeletedException.RESOURCE_COULD_NOT_BE_DELETED;
import static com.stathis.cardcostapi.exception.ResourceNotFoundException.RESOURCE_NOT_FOUND_WITH_ID;
import static com.stathis.cardcostapi.exception.ResourceNotUpdatedException.RESOURCE_COULD_NOT_BE_UPDATED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CardClearingServiceImplTest {

    @Mock
    CardClearingRepository cardClearingRepository;

    CardClearingService cardClearingService;

    CardClearing cardClearingGR;
    CardClearing cardClearingUS;
    CardClearing cardClearingForSave;
    CardClearing cardClearingForUpdate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cardClearingService = new CardClearingServiceImpl(cardClearingRepository);

        cardClearingGR = CardClearing.builder().id(1L).countryCode("GR").clearingCost(15D).build();
        cardClearingUS = CardClearing.builder().id(2L).countryCode("US").clearingCost(5D).build();
        cardClearingForSave = CardClearing.builder().countryCode("GR").clearingCost(15D).build();
        cardClearingForUpdate = CardClearing.builder().countryCode("updated_country_code").build();
    }

    @Test
    void getAllCardClearings() {

        List<CardClearing> cardClearingList = new ArrayList<>();

        cardClearingList.add(cardClearingGR);
        cardClearingList.add(cardClearingUS);

        when(cardClearingService.getAllCardClearings()).thenReturn(cardClearingList);

        cardClearingService.getAllCardClearings();

        verify(cardClearingRepository, times(1)).findAll();
    }

    @Test
    void getCardClearingById() {

        when(cardClearingRepository.findById(anyLong())).thenReturn(Optional.ofNullable(cardClearingGR));

        cardClearingService.getCardClearingById(anyLong());

        verify(cardClearingRepository, times(1)).findById(anyLong());
    }

    @Test
    void getCardClearingByIdNotFound() {

        when(cardClearingRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> cardClearingService.getCardClearingById(1L));

        String expectedMessage = RESOURCE_NOT_FOUND_WITH_ID + 1L;

        assertNotNull(exception);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getCardClearingByCountryCode() {

        when(cardClearingService.getCardClearingByCountryCode(anyString())).thenReturn(cardClearingGR);

        cardClearingService.getCardClearingByCountryCode(anyString());

        verify(cardClearingRepository, times(1)).findCardClearingByCountryCode(anyString());
    }

    @Test
    void saveCardClearing() {

        when(cardClearingService.saveCardClearing(cardClearingForSave)).thenReturn(cardClearingGR);

        cardClearingService.saveCardClearing(cardClearingForSave);

        verify(cardClearingRepository, times(1)).save(cardClearingForSave);
    }

    @Test
    void updateCardClearing() {

        when(cardClearingRepository.findById(anyLong())).thenReturn(Optional.ofNullable(cardClearingGR));

        cardClearingService.getCardClearingById(anyLong());

        verify(cardClearingRepository, times(1)).findById(anyLong());

        when(cardClearingService.saveCardClearing(cardClearingForSave)).thenReturn(cardClearingGR);

        cardClearingService.saveCardClearing(cardClearingForSave);

        verify(cardClearingRepository, times(1)).save(cardClearingForSave);
    }

    @Test
    void updateCardClearingResourceNotFound() {

        when(cardClearingRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotUpdatedException exception = assertThrows(ResourceNotUpdatedException.class,
                () -> cardClearingService.updateCardClearing(1L, cardClearingForUpdate));

        String expectedMessage = RESOURCE_COULD_NOT_BE_UPDATED + RESOURCE_NOT_FOUND_WITH_ID + 1L;

        assertNotNull(exception);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void deleteCardClearingById() {

        cardClearingService.deleteCardClearingById(anyLong());

        verify(cardClearingRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteCardClearingByIdResourceNotFound() {

        doThrow(EmptyResultDataAccessException.class).when(cardClearingRepository).deleteById(anyLong());

        ResourceNotDeletedException exception = assertThrows(ResourceNotDeletedException.class, () -> cardClearingService.deleteCardClearingById(1L));

        String expectedMessage = RESOURCE_COULD_NOT_BE_DELETED + "1";

        assertNotNull(exception);
        assertEquals(expectedMessage, exception.getMessage());
    }
}