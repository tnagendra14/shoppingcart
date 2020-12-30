package com.shopping.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.cart.model.ShoppingCartRequest;
import com.shopping.cart.model.ShoppingCartResponse;
import com.shopping.cart.service.ShoppingCartService;

@RestController
@RequestMapping("orders/v1")
public class ShoppingCartController {
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@PostMapping(value = "/customerbill")
	public ShoppingCartResponse getDetailedBill(@RequestBody ShoppingCartRequest shoppingCartRequest) {
		return shoppingCartService.returnDetailedTotalBill(shoppingCartRequest);
		
	}

}
