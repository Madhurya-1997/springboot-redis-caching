package com.example.springrediscache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("/")
    public String welcome() {
        return "Welcome to product service !!";
    }

    @GetMapping("/get")
    @Cacheable(value= "products")
    public List<Product> fetchAllProducts() {
        log.info(">> fetching all products ");
        return productService.findAll();
    }


    @GetMapping("/{code}")
    @Cacheable(value= "products", key="#code")
    public Product fetchProduct(@PathVariable String code) throws Exception{
        log.info(">> fetching product with code: " + code);
        return productService.find(code);
    }

    @PostMapping("/create")
    public Product addProduct(@RequestBody Product product) throws Exception {
        log.info(">> creating product with code: " + product.getCode());
        return productService.add(product);
    }

    @PutMapping("/update")
    @CachePut(value="products", key="#product")
    public Product updateProduct(@RequestBody Product product) throws Exception {
        log.info(">> updating product with code: " + product.getCode());
        return productService.update(product);
    }

    @DeleteMapping("/remove/{code}")
    @CacheEvict(value="products", allEntries = false, key="#code")
    public String deleteProduct(@PathVariable String code) throws Exception {
        log.info(">> deleting product with code: " + code);
        return productService.remove(code);
    }

    @DeleteMapping("/remove")
    @CacheEvict(value="products", allEntries = false)
    public String deleteProduct() {
        log.info(">> deleting all products");
        return productService.removeAll();
    }
}


