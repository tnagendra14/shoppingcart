package com.shopping.cart.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShoppingCartResponse {
	
	private String subTotal;
	private String tax;
	private Map<String,Integer> discounts;
	private String total;
	private String comments;

}
