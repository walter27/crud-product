package com.produc.infraestructure.controllers.models;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

	private Long id;
	private String name;
	private BigDecimal price;
	private Integer stock;

}
