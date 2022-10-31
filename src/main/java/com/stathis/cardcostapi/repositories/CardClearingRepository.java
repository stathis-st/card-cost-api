package com.stathis.cardcostapi.repositories;

import com.stathis.cardcostapi.domain.CardClearing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardClearingRepository extends JpaRepository<CardClearing, Long> {
    CardClearing findCardClearingByCountryCode(String countryCode);
}
