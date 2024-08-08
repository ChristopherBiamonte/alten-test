package com.backendtest.product_api.controller;

import com.backendtest.product_api.dto.ProductDTO;
import com.backendtest.product_api.model.Product;
import com.backendtest.product_api.service.ProductsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductsServices productsServices;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO product){
        Product newProduct =  productsServices.createProduct(product);
        return new ResponseEntity<Product>(newProduct, HttpStatus.CREATED);
    }

    @GetMapping
    public List<ProductDTO> getAllProducts(){
        return productsServices.getAllProducts();
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable int id){
        ProductDTO product = productsServices.getProductById(id);
        return new ResponseEntity<ProductDTO>(product, HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<ProductDTO> updateProductFields(@PathVariable int id, @RequestBody Map<String, Object> fields){
        ProductDTO product = productsServices.updateProductByFields(id, fields );
        return new ResponseEntity<ProductDTO>(product, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        productsServices.deleteProductById(id);
        return new ResponseEntity<String>("Product deleted Successfully", HttpStatus.NO_CONTENT);
    }
}
