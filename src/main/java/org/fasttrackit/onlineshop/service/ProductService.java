package org.fasttrackit.onlineshop.service;

import org.fasttrackit.onlineshop.domain.Product;
import org.fasttrackit.onlineshop.exeption.ResourceNotFoundException;
import org.fasttrackit.onlineshop.persistance.ProductRepository;
import org.fasttrackit.onlineshop.transfer.product.GetProductRequests;
import org.fasttrackit.onlineshop.transfer.product.SaveProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    // IoC - inversion of control
    private final ProductRepository productRepository;

    //dependency injection
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //lombok - to check home
    public Product createProduct (SaveProductRequest request) {
        LOGGER.info("Creating product: {}", request);

        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setQuantity(request.getQuantity());
        product.setImagePath(request.getImagePath());

        return productRepository.save(product);
    }

    public Product getProduct (long id){
        LOGGER.info("Retrieving prodcut {}", id);
        return productRepository.findById(id)
                //lambda expressions
                .orElseThrow(() -> new ResourceNotFoundException("Product " + id + " not found."));

    }

    public Page<Product> getProducts(GetProductRequests request, Pageable pageable){
        LOGGER.info("Retrieving products: {}",request);
        if (request != null && request.getPartialName() != null && request.getMinimumQuantity() != null){
            return productRepository.findByNameContainingAndQuantityGreaterThanEqual(request.getPartialName(),request.getMinimumQuantity(),pageable);
        } else if (request != null && request.getPartialName()!= null){
            return productRepository.findByNameContaining(request.getPartialName(),pageable);
        } else {
            return productRepository.findAll(pageable);
        }
    }

    public Product updateProduct (long id, SaveProductRequest request){
        LOGGER.info("Updating product {}: {}",id,request);
        Product product = getProduct(id);
        //replaces setters
        BeanUtils.copyProperties(request,product);

        return productRepository.save(product);
        }

     public void deleteProduct (long id) {
        LOGGER.info("Deleting product {}",id);
        productRepository.deleteById(id);
     }
}
