package com.mohirdev.mohirdev.web.rest;

import com.mohirdev.mohirdev.domain.User;
import com.mohirdev.mohirdev.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserResource {
    private  final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity create(@RequestBody User user){

        User result=userService.save(user);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/users")
    public ResponseEntity getAll(){
        return ResponseEntity.ok(userService.findAll());
    }
}
