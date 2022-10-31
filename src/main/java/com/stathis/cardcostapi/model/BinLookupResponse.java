package com.stathis.cardcostapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BinLookupResponse{
	private Number number;
	private String scheme;
	private String type;
	private String brand;
	private Boolean prepaid;
	private Country country;
	private Bank bank;
}
