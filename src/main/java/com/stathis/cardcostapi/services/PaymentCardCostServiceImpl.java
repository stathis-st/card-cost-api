package com.stathis.cardcostapi.services;

import com.stathis.cardcostapi.domain.CardClearing;
import com.stathis.cardcostapi.model.BinLookupResponse;
import com.stathis.cardcostapi.model.CardClearingCostRequest;
import com.stathis.cardcostapi.model.CardClearingCostResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@AllArgsConstructor
public class PaymentCardCostServiceImpl implements PaymentCardCostService {

    private final RestTemplate restTemplate;
    private final CardClearingService cardClearingService;

    @Override
    public CardClearingCostResponse calculateCardClearingCost(CardClearingCostRequest cardClearingCostRequest) {

        BinLookupResponse binLookupResponse = getBinDetailsFromExternalApi(cardClearingCostRequest);

        String countryCode = binLookupResponse.getCountry().getAlpha2();

        CardClearing cardClearing = cardClearingService.getCardClearingByCountryCode(countryCode);

        if (Objects.isNull(cardClearing)) {
            return CardClearingCostResponse.builder()
                    .country("Others")
                    .cost(10D)
                    .build();
        } else {
            return CardClearingCostResponse.builder()
                    .country(cardClearing.getCountryCode())
                    .cost(cardClearing.getClearingCost())
                    .build();
        }
    }
    private BinLookupResponse getBinDetailsFromExternalApi(CardClearingCostRequest cardClearingCostRequest) {
        String binNumber = cardClearingCostRequest.getCardNumber().substring(0, 8);
        ResponseEntity<BinLookupResponse> response = restTemplate
                .getForEntity("https://lookup.binlist.net/" + binNumber, BinLookupResponse.class);

        return response.getBody();
    }
}
