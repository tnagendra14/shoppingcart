package com.shopping.cart.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.shopping.cart.model.ShoppingCartRequest;
import com.shopping.cart.model.ShoppingCartResponse;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartServiceTest {
	
	@InjectMocks
	private ShoppingCartService shoppingCartService;
	
	private List<String> products;
	private ShoppingCartResponse shoppingCartResponse;
	private ShoppingCartRequest shoppingCartRequest;
	@Before
	public void setUp() throws Exception {
		products=new ArrayList<>();
		
	}
	
	
	@Test
	public void shouldReturn_detailedBillForTheAddedProductsToTheCart() {
		products.add("T-shirt");
		products.add("T-shirt");
		products.add("Shoes");
		products.add("Jacket");
		shoppingCartResponse=new ShoppingCartResponse();
		shoppingCartResponse.setSubTotal(String.valueOf(8500));
		shoppingCartResponse.setTax(String.valueOf(1530));
		shoppingCartResponse.setTotal(String.valueOf(8280));
		shoppingCartResponse.setComments("2 eligible offers");
		
		shoppingCartRequest=new ShoppingCartRequest();
		shoppingCartRequest.setProducts(products);
		
		ShoppingCartResponse response=shoppingCartService.returnDetailedTotalBill(shoppingCartRequest);
		assertEquals(shoppingCartResponse.getSubTotal(), response.getSubTotal());
		assertEquals(shoppingCartResponse.getTax(), response.getTax());
		assertEquals(shoppingCartResponse.getTotal(), response.getTotal());
		assertEquals(shoppingCartResponse.getComments(),response.getComments());
		assertNotNull(response.getDiscounts());
		
	}
	
	@Test
	public void shouldReturn_detailedBillForTheAddedProductsToTheCartWhenNoOffersEligible() {
		products.add("T-shirt");
		products.add("Trousers");
		shoppingCartResponse=new ShoppingCartResponse();
		shoppingCartResponse.setSubTotal(String.valueOf(2000));
		shoppingCartResponse.setTax(String.valueOf(360));
		shoppingCartResponse.setTotal(String.valueOf(2360));
		shoppingCartResponse.setComments("No eligible offers");
		
		shoppingCartRequest=new ShoppingCartRequest();
		shoppingCartRequest.setProducts(products);
		
		ShoppingCartResponse response=shoppingCartService.returnDetailedTotalBill(shoppingCartRequest);
		assertEquals(shoppingCartResponse.getSubTotal(), response.getSubTotal());
		assertEquals(shoppingCartResponse.getTax(), response.getTax());
		assertEquals(shoppingCartResponse.getTotal(), response.getTotal());
		assertEquals(shoppingCartResponse.getComments(),response.getComments());
		assertNull(response.getDiscounts());
		
	}

}
