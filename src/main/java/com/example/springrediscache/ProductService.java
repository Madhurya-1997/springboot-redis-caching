package com.example.springrediscache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product find(String code) throws Exception {
        Optional<Product> product = productRepository.findByCode(code);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new Exception("Product with code: " + code + " not found !!");
        }
    }

    public Product add(Product product) throws Exception {
        Optional<Product> p = productRepository.findByCode(product.getCode());

        if (p.isEmpty()) {
            return productRepository.save(product);
        } else {
            throw new Exception ("Product with code: " + p.get().getCode() + " already exists !!");
        }
    }

    public Product update(Product product) throws Exception {
        Optional<Product> p = productRepository.findByCode(product.getCode());

        if (p.isPresent()) {
            return productRepository.save(product);
        } else {
            throw new Exception ("Product with code: " + product.getCode() + " already exists !!");
        }
    }

    public String remove(String code) throws Exception {
        Optional<Product> p = productRepository.findByCode(code);

        if (p.isPresent()) {
            productRepository.deleteByCode(code);
            return "Product deleted successfully";
        } else {
            throw new Exception ("Product with code: " + code + " not found !!");
        }
    }

    public String removeAll() {
        productRepository.deleteAll();
        return "All products deleted successfully";
    }
}
