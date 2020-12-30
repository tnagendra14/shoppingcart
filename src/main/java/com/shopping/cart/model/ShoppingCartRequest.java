package com.shopping.cart.model;

import java.util.List;

import lombok.Data;

@Data
public class ShoppingCartRequest {
	
	private List<String> products;

}
