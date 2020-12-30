# shoppingcart
Program that can price a shopping cart of products, accept multiple products, combine applicable offers, apply tax, and display the detailed total bill.

#Instructions to run this project

#To run project and provide inputs via Command Line Tool :

1.Go to ShoppingCartService.class and Run as Java Application
2.Enter products from console ,for example:T-shirt T-shirt Shoes Jacket

Note: Leave space between each product while adding products from console

output will be :

Subtotal: Rs.8500
Tax: Rs.1530
Discounts:
10% off on shoes: -Rs.500
50% off on jacket: -Rs.1250
Total: Rs.8280
2 eligible offers


#To Run project as a Spring Boot application and to test via Rest API from postman or any rest client tool, go to com.shopping.cart.ShoppingCartApplication class and Run as Spring Boot App

1.Default port is 8080 hence to test this from POSTMAN add the below URL and provide Request body as below,

Project URL :

localhost:8080/orders/v1/customerbill

Body:

{
    "products":["T-shirt", "Trousers"]
}

outPut:

{
    "subTotal": "2000",
    "tax": "360",
    "total": "2360",
    "comments": "No eligible offers"
}

