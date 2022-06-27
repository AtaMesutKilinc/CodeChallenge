package com.challenge.restcontrollers;

import com.challenge.entities.Product;
import com.challenge.entities.User;
import com.challenge.services.ProductService;
import com.challenge.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductRestController {
    final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/save")
    public ResponseEntity save(@Valid @RequestBody Product product){
        return productService.save(product);
    }

    @GetMapping("/list")
    public ResponseEntity list(){
        return productService.list();
    }

    @GetMapping("/myProductslist")
    public ResponseEntity myProductslist(@RequestParam Long id){
        return productService.MyProductsList(id);
    }

    @PutMapping("/update")
    public ResponseEntity update(@Valid @RequestBody Product product){
        return productService.update(product);
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam Long id){
        return  productService.delete(id);
    }
}
