package com.backendtest.product_api.service;

import com.backendtest.product_api.dto.ProductDTO;
import com.backendtest.product_api.model.Product;
import com.backendtest.product_api.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductsServices {
    @Autowired
    private ProductsRepository productsRepository;

    public Product createProduct(ProductDTO product){
        try{
            Product productModel = this.convertToModel(product);
            return productsRepository.save(productModel);
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid body");
        }
    }

    public List<ProductDTO> getAllProducts(){
        List<Product> allProducts = productsRepository.findAll();

        ArrayList<ProductDTO> allProductsDTO = new ArrayList<>();
        allProducts.forEach(product -> {
            allProductsDTO.add(this.convertToDto(product));
        });
        return allProductsDTO;
    }

    public ProductDTO getProductById(int id){
        Product product = productsRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        return this.convertToDto(product);
    }

    public ProductDTO updateProductByFields(int id, Map<String,Object> fields){
        Optional<Product> existingProduct = productsRepository.findById(id);

        if(existingProduct.isPresent()){
            fields.forEach((key,value) -> {
                try {
                    Field field = Product.class.getDeclaredField(key);
                    field.setAccessible(true);
                    field.set(existingProduct.get(), value);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid attribute " + key);
                } catch(IllegalArgumentException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong type on attribute " + key);
                }
            });
            Product productSave = productsRepository.save(existingProduct.get());
            return this.convertToDto(productSave);
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
    }

    public void deleteProductById(int id){
        if(!productsRepository.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        productsRepository.deleteById(id);
    }

    private Product convertToModel(ProductDTO product){
        Product productModel = new Product();

        productModel.setCode(product.getCode());
        productModel.setCategory(product.getCategory());
        productModel.setImage(product.getImage());
        productModel.setDescription(product.getDescription());
        productModel.setInventoryStatus(product.getInventoryStatus());
        productModel.setPrice(product.getPrice());
        productModel.setQuantity(product.getQuantity());
        productModel.setRating(product.getRating());
        productModel.setName(product.getName());


        return productModel;

    }

    private ProductDTO convertToDto(Product product){
        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(product.getId());
        productDTO.setCode(product.getCode());
        productDTO.setCategory(product.getCategory());
        productDTO.setImage(product.getImage());
        productDTO.setDescription(product.getDescription());
        productDTO.setInventoryStatus(product.getInventoryStatus());
        productDTO.setPrice(product.getPrice());
        productDTO.setQuantity(product.getQuantity());
        productDTO.setRating(product.getRating());
        productDTO.setName(product.getName());

        return productDTO;
    }
}
