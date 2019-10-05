package org.fasttrackit.onlineshop;

import org.fasttrackit.onlineshop.domain.Product;
import org.fasttrackit.onlineshop.exeption.ResourceNotFoundException;
import org.fasttrackit.onlineshop.service.ProductService;
import org.fasttrackit.onlineshop.steps.ProductSteps;
import org.fasttrackit.onlineshop.transfer.product.SaveProductRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceIntegrationTests {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductSteps productSteps;

    @Test
    public void testCreateProduct_whenValidRequest_thanReturnCreatedProduct() {
        productSteps.createProduct();
    }


    @Test(expected = TransactionSystemException.class)
    public void testCreateProduct_whenInvalidRequest_thanThrowException(){
        SaveProductRequest request = new SaveProductRequest();

        //not setting any values on the request, because we want to send an invalid request
        Product product = productService.createProduct(request);
    }

    @Test
    public void testGetProduct_whenExistingEntity_thanReturnProduct (){
        Product createdProduct = productSteps.createProduct();

        Product retrievedProduct = productService.getProduct(createdProduct.getId());
        assertThat(retrievedProduct,notNullValue());
        assertThat(retrievedProduct.getId(),is(createdProduct.getId()));
        assertThat(retrievedProduct.getName(),is(retrievedProduct.getName()));

    }

    @Test (expected = ResourceNotFoundException.class)
    public void testGetProduct_whenNonExistingEntity_thanThrowNotFoundException (){
        productService.getProduct(99999);
    }

    @Test
    public void testUpdateProduct_whenValidRequest_thanReturnUpdatedProduct (){
        Product createdProduct = productSteps.createProduct();
        SaveProductRequest request = new SaveProductRequest();
        request.setName(createdProduct.getName()+ "Updated");
        request.setPrice(createdProduct.getPrice()+ 10);
        request.setQuantity(createdProduct.getQuantity() +10);
        Product updatedProduct = productService.updateProduct(createdProduct.getId(), request);

        assertThat(updatedProduct,notNullValue());
        assertThat(updatedProduct.getId(),is(createdProduct.getId()));
        assertThat(updatedProduct.getName(),is(request.getName()));
        assertThat(updatedProduct.getPrice(),is(request.getPrice()));
        assertThat(updatedProduct.getQuantity(),is(request.getQuantity()));

    }


}
