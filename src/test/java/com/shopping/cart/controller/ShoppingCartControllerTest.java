package com.shopping.cart.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.cart.model.ShoppingCartRequest;
import com.shopping.cart.service.ShoppingCartService;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartControllerTest {
	
	
	@InjectMocks
	private ShoppingCartController shoppingCartController;
	
	@Mock
	private ShoppingCartService shoppingCartService;
	
	private MockMvc mockMvc;
	
	private ShoppingCartRequest shoppingCartRequest;
	private List<String> products;
	
	@Before
	public void setUp() throws Exception {
		mockMvc=MockMvcBuilders.standaloneSetup(shoppingCartController).build();
		products=new ArrayList<>();
		products.add("T-shirt");
		products.add("T-shirt");
		products.add("Shoes");
		products.add("Jacket");
		shoppingCartRequest=new ShoppingCartRequest();
		shoppingCartRequest.setProducts(products);
	}
	
	
	@Test
	public void shoppingCartController_ShouldReturnOkStatusWhenEndPointIsCorrect() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post("/orders/v1/customerbill").content(new ObjectMapper().writeValueAsString(shoppingCartRequest))
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void shoppingCartController_ShouldReturnNotFoundStatusWhenEndPointIsInCorrect() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post("/orders/v1/customerbills").content(new ObjectMapper().writeValueAsString(shoppingCartRequest))
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}

}
