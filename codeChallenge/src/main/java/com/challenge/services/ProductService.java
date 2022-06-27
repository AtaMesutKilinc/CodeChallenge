package com.challenge.services;

import com.challenge.entities.Product;
import com.challenge.entities.User;
import com.challenge.repositories.ProductRepository;
import com.challenge.repositories.UserRepository;
import com.challenge.utils.REnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {
    final ProductRepository productRepository;
    final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<Map<REnum,Object>> save(Product product){
        Map<REnum,Object> hashMap= new LinkedHashMap<>();
        try {
            Optional<User> optionalUser= userRepository.findById(product.getUser().getId());
            if (optionalUser.isPresent()){
                productRepository.save(product);
                hashMap.put(REnum.status,true);
                hashMap.put(REnum.result,product);
                return new ResponseEntity<>(hashMap, HttpStatus.OK);
            }else{
                hashMap.put(REnum.status, false);
                hashMap.put(REnum.message, "There is no such user or please select the correct user");
                return new  ResponseEntity(hashMap, HttpStatus.BAD_REQUEST);
            }

        }catch (Exception ex){
            hashMap.put(REnum.status, false);
            hashMap.put(REnum.message, ex.getMessage());
            return new  ResponseEntity(hashMap, HttpStatus.BAD_REQUEST);
        }


    }

    public ResponseEntity<Map<String ,Object>> update(Product product){
        Map<REnum,Object> hashMap = new LinkedHashMap<>();
        try{
            Optional<Product> optionalProduct = productRepository.findById(product.getId());
            if(optionalProduct.isPresent()){
                productRepository.saveAndFlush(product);
                hashMap.put(REnum.result, product);
                hashMap.put(REnum.status, true);
                return new  ResponseEntity(hashMap, HttpStatus.OK);
            }else{
                hashMap.put(REnum.status, false);
                return new  ResponseEntity(hashMap, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            hashMap.put(REnum.status, false);
            hashMap.put(REnum.message, ex.getMessage());
            return new  ResponseEntity(hashMap, HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<Map<REnum,Object>> delete(Long id){
        Map<REnum,Object> hashMap =new LinkedHashMap<>();
        try {

            productRepository.deleteById(id);
            hashMap.put(REnum.status,true);
            hashMap.put(REnum.message,"product delete success");
            return new ResponseEntity<>(hashMap, HttpStatus.OK);

        }catch (Exception ex){
            hashMap.put(REnum.status,false);
            hashMap.put(REnum.message,ex.getMessage());
            return new ResponseEntity<>(hashMap, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Map<REnum,Object>> list(){
        Map<REnum,Object> hashMap =new LinkedHashMap<>();
        hashMap.put(REnum.status,true);
        hashMap.put(REnum.result,productRepository.findAll());
        return new ResponseEntity<>(hashMap, HttpStatus.OK);
    }

    public ResponseEntity<Map<REnum,Object>> MyProductsList(Long id){
        Map<REnum,Object> hashMap =new LinkedHashMap<>();
        List<Product> products = productRepository.findByUser_IdEquals(id);
        hashMap.put(REnum.status,true);
        hashMap.put(REnum.result,products);
        return new ResponseEntity<>(hashMap, HttpStatus.OK);
    }

}
