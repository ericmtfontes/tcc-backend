package com.api.tcc.controllers;

import com.api.tcc.models.UserModel;
import com.api.tcc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> registerNewUserAccount(@RequestBody UserModel userModel){
        userService.registerNewUserAccount(userModel);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
