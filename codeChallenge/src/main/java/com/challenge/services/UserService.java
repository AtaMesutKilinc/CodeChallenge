package com.challenge.services;

import com.challenge.entities.User;
import com.challenge.repositories.UserRepository;
import com.challenge.utils.REnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<Map<REnum,Object>> save(User user){

        Map<REnum,Object> hashMap= new LinkedHashMap<>();
        Optional<User> optionalUser= userRepository.findByEmailEquals(user.getEmail());
        if (!optionalUser.isPresent()){
            userRepository.save(user);
            hashMap.put(REnum.status,true);
            hashMap.put(REnum.message,"User registration Successful");
            hashMap.put(REnum.result, user);
            return new ResponseEntity<>(hashMap, HttpStatus.OK);
        }else{

            hashMap.put(REnum.status,false);
            hashMap.put(REnum.message, "Email already exists: "+user.getEmail());
            return new ResponseEntity<>(hashMap, HttpStatus.OK);
        }


    }

    public ResponseEntity<Map<REnum,Object>> update(User user){
        Map<REnum,Object> hashMap= new LinkedHashMap<>();
        try {
            Optional<User> optionalUser= userRepository.findById(user.getId());
            if (optionalUser.isPresent()){
                userRepository.saveAndFlush(user);
                hashMap.put(REnum.status,true);
                hashMap.put(REnum.message,"User update successful");
                hashMap.put(REnum.result, user);
                return new  ResponseEntity(hashMap, HttpStatus.OK);
            }else {
                hashMap.put(REnum.status,false);
                hashMap.put(REnum.message,"User is null! try again");
                return new  ResponseEntity(hashMap, HttpStatus.BAD_REQUEST);
            }

        }catch (Exception ex){
            hashMap.put(REnum.status,false);
            hashMap.put(REnum.message,ex.getMessage());
            return new  ResponseEntity(hashMap, HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<Map<REnum,Object>> delete(Long id){
        Map<REnum,Object> hashMap =new LinkedHashMap<>();
        try {
            userRepository.deleteById(id);
            hashMap.put(REnum.status,true);
            hashMap.put(REnum.message,"User delete successful");
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
        hashMap.put(REnum.result,userRepository.findAll());
        return new ResponseEntity<>(hashMap, HttpStatus.OK);
    }

}
