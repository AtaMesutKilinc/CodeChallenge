package com.challenge.restcontrollers;

import com.challenge.entities.User;
import com.challenge.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserRestController {
    final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/save")
    public ResponseEntity save(@Valid @RequestBody User user){
        return userService.save(user);
    }

    @GetMapping("/list")
    public ResponseEntity list(){
        return userService.list();
    }

    @PutMapping("/update")
    public ResponseEntity update(@Valid @RequestBody User user){
        return userService.update(user);
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam Long id){
        return  userService.delete(id);
    }
}
