package com.shopping.cart.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.shopping.cart.exception.EmptyCartException;
import com.shopping.cart.model.ShoppingCartRequest;
import com.shopping.cart.model.ShoppingCartResponse;
import com.shopping.cart.util.ShoppingConstants;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShoppingCartService {
	
	@Value("${shoes.discount}")
	private Integer shoesDiscountPercentage;
	
	@Value("${jacket.discount}")
	private Integer jacketDiscountPercentage;

	/** To run program via Command Line Tool */
	public static void main(String[] args) {
		log.info(ShoppingConstants.ADD_TO_CART);
		Scanner sc = new Scanner(System.in);
		String products = sc.nextLine();
		ShoppingCartResponse shoppingCartResponse=new ShoppingCartService().processAndValidateProducts(products);
		if(shoppingCartResponse!=null) {
			log.info(ShoppingConstants.SUB_TOTAL + shoppingCartResponse.getSubTotal());
			log.info(ShoppingConstants.TAX + shoppingCartResponse.getTax());
			if(shoppingCartResponse.getDiscounts()!=null) {
				log.info(ShoppingConstants.DISCOUNTS);
				log.info(String.valueOf(10)+ShoppingConstants.PERCENTAGE_SHOES + shoppingCartResponse.getDiscounts().get(String.valueOf(10)+ShoppingConstants.PERCENTAGE_SHOES));
				log.info(String.valueOf(50)+ShoppingConstants.PERCENTAGE_JACKET + shoppingCartResponse.getDiscounts().get(String.valueOf(50)+ShoppingConstants.PERCENTAGE_JACKET));
				
			}
			log.info(ShoppingConstants.TOTAL+shoppingCartResponse.getTotal().toString());
			log.info(shoppingCartResponse.getComments());
		}else
			log.info(ShoppingConstants.EMPTY_CART_ERROR_MESSAGE);
		sc.close();
	}
	 

	private ShoppingCartResponse processAndValidateProducts(String products) {
		Map<String, Integer> map = new HashMap<>();
		if (products != null && !products.isEmpty()) {
			String[] productArray = products.split("\\s");

			for (int i = 0; i < productArray.length; i++) {
				Integer count = map.get(productArray[i]);
				map.put(productArray[i], count != null ? map.get(productArray[i]) + 1 : 1);

			}
			return calculateDetailedTotalBill(map);
		}
			return null;
	}
	
	
	public ShoppingCartResponse returnDetailedTotalBill(ShoppingCartRequest shoppingCartRequest) {
		Map<String, Integer> map = new HashMap<>();
	     if(!CollectionUtils.isEmpty(shoppingCartRequest.getProducts())) {
	    	 for(String product:shoppingCartRequest.getProducts()) {
	    		Integer count= map.get(product);
	    		map.put(product, count != null ? map.get(product) + 1 : 1);
	    	 }
	     }else throw new EmptyCartException(ShoppingConstants.EMPTY_CART_ERROR_MESSAGE);
	     
	     return calculateDetailedTotalBill(map);
	}
	
    /**Core Logic to calculate Detailed Total Bill*/
	private ShoppingCartResponse calculateDetailedTotalBill(Map<String, Integer> map) {
		ShoppingCartResponse cartResponse=null;
		Map<String,Integer> discounts=new LinkedHashMap<>();
		List<Integer> finalPrice = new ArrayList<>();
		Integer subTotal = 0;
		Integer tax = 0;
		Integer shoesDiscount = 0;
		Integer jacketDiscount = 0;
		Integer total = 0;
		Map<String, Integer> eligibleOffers = new HashMap<>();
		
		Integer jacketPercentage=jacketDiscountPercentage!=null?jacketDiscountPercentage:50;
		Integer shoesPercentage=shoesDiscountPercentage!=null?shoesDiscountPercentage:10;

		for (Entry<String, Integer> product : map.entrySet()) {
			if (ShoppingConstants.T_SHIRT.equalsIgnoreCase(product.getKey()) && product.getValue() % 2 == 0) {
				finalPrice.add(availableProducts().get(ShoppingConstants.T_SHIRT) * product.getValue());
				int jacketCount = product.getValue() / 2;
				jacketDiscount = availableProducts().get(ShoppingConstants.JACKET) * jacketCount;
				jacketDiscount = (jacketDiscount * jacketPercentage) / 100;
				eligibleOffers.put(ShoppingConstants.JACKET_OFFER, 1);
			}
			if (ShoppingConstants.T_SHIRT.equalsIgnoreCase(product.getKey()) && product.getValue() % 2 != 0) {
				finalPrice.add(availableProducts().get(ShoppingConstants.T_SHIRT) * product.getValue());
			}

			if (ShoppingConstants.SHOES.equalsIgnoreCase(product.getKey())) {
				finalPrice.add(availableProducts().get(ShoppingConstants.SHOES) * product.getValue());
				shoesDiscount = availableProducts().get(ShoppingConstants.SHOES) * product.getValue();
				shoesDiscount = (shoesDiscount * shoesPercentage) / 100;
				eligibleOffers.put(ShoppingConstants.SHOES_OFFER, 1);
			}

			if (ShoppingConstants.JACKET.equalsIgnoreCase(product.getKey())) {
				finalPrice.add(availableProducts().get(ShoppingConstants.JACKET) * product.getValue());
			}

			if (ShoppingConstants.TROUSERS.equalsIgnoreCase(product.getKey())) {
				finalPrice.add(availableProducts().get(ShoppingConstants.TROUSERS) * product.getValue());
			}
		}
        /** To calculate subtotal*/
		subTotal = finalPrice.stream().reduce(0, (a, b) -> a + b);
		
		/** To calculate tax*/
		tax = (subTotal * 18) / 100;

		if (!eligibleOffers.isEmpty()) {
			cartResponse=new ShoppingCartResponse();
			Integer shoes_Offer = eligibleOffers.get(ShoppingConstants.SHOES_OFFER) != null ? eligibleOffers.get(ShoppingConstants.SHOES_OFFER) : 0;
			Integer jacket_Offer = eligibleOffers.get(ShoppingConstants.JACKET_OFFER) != null ? eligibleOffers.get(ShoppingConstants.JACKET_OFFER) : 0;
			
			cartResponse.setSubTotal(String.valueOf(subTotal));
			cartResponse.setTax(String.valueOf(tax));
			if (shoesDiscount != 0) {
				discounts.put(String.valueOf(shoesPercentage)+ShoppingConstants.PERCENTAGE_SHOES, shoesDiscount);
				cartResponse.setDiscounts(discounts);
			}
			if (jacketDiscount != 0) {
				discounts.put(String.valueOf(jacketPercentage)+ShoppingConstants.PERCENTAGE_JACKET, jacketDiscount);
				cartResponse.setDiscounts(discounts);
			}
			cartResponse.setComments(String.valueOf(shoes_Offer + jacket_Offer)+ShoppingConstants.ELIGIBLE_OFFERS);
			subTotal = subTotal - shoesDiscount - jacketDiscount;
			total = subTotal + tax;
			cartResponse.setTotal(String.valueOf(total));
		} else {
			cartResponse=new ShoppingCartResponse();
			cartResponse.setSubTotal(String.valueOf(subTotal));
			cartResponse.setTax(String.valueOf(tax));
			total = subTotal + tax;
			cartResponse.setTotal(String.valueOf(total));
			cartResponse.setComments(ShoppingConstants.NO_ELIGIBLE_OFFERS);
		}
		return cartResponse;

	}

	/** Available products and corresponding prices*/
	public static Map<String, Integer> availableProducts() {
		Map<String, Integer> availableProdcuts = new HashMap<>();
		availableProdcuts.put(ShoppingConstants.T_SHIRT, 500);
		availableProdcuts.put(ShoppingConstants.TROUSERS, 1500);
		availableProdcuts.put(ShoppingConstants.JACKET, 2500);
		availableProdcuts.put(ShoppingConstants.SHOES, 5000);
		return availableProdcuts;

	}

}
