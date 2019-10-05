package org.fasttrackit.onlineshop;

import org.fasttrackit.onlineshop.domain.Customer;
import org.fasttrackit.onlineshop.domain.Product;
import org.fasttrackit.onlineshop.service.CartService;
import org.fasttrackit.onlineshop.steps.CustomerSteps;
import org.fasttrackit.onlineshop.steps.ProductSteps;
import org.fasttrackit.onlineshop.transfer.cart.AddProductToCartRequest;
import org.fasttrackit.onlineshop.transfer.cart.CartResponse;
import org.fasttrackit.onlineshop.transfer.product.ProductInCartResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceIntegrationTests {

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerSteps customerSteps;

    @Autowired
    private ProductSteps productSteps;

    @Test
    public void testAddToCart_whenNewCart_thenCreateCart(){
        Customer customer = customerSteps.createCustomer();
        Product product = productSteps.createProduct();

        AddProductToCartRequest request = new AddProductToCartRequest();
        request.setCustomerId(customer.getId());
        request.setProductId(product.getId());
        cartService.addProductToCart(request);

        CartResponse cartResponse = cartService.getCart(customer.getId());
        assertThat(cartResponse, notNullValue());
        assertThat(cartResponse.getId(),is(customer.getId()));
        assertThat(cartResponse.getProducts(), notNullValue());
        assertThat(cartResponse.getProducts(),hasSize(1));

        ProductInCartResponse productFromCart = cartResponse.getProducts().iterator().next();

        assertThat(productFromCart,notNullValue());
        assertThat(productFromCart.getId(),is(request.getProductId()));
        assertThat(productFromCart.getName(),is(product.getName()));
        assertThat(productFromCart.getPrice(),is(product.getPrice()));
    }
}
