package com.stathis.cardcostapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CardClearingCostRequest {

    @JsonProperty("card_number")
    @Size(min = 16, max = 16, message = "Card number must have 16 characters length")
    private String cardNumber;
}
