package com.stathis.cardcostapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Country{
	private String numeric;
	private String alpha2;
	private String name;
	private String emoji;
	private String currency;
	private Integer latitude;
	private Integer longitude;
}
