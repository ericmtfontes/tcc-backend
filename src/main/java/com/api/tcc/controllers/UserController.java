package com.api.tcc.controllers;

import com.api.tcc.dto.UserDTO;
import com.api.tcc.models.UserModel;
import com.api.tcc.services.TokenService;
import com.api.tcc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> registerNewUserAccount(@RequestBody UserModel userModel){
        userService.registerNewUserAccount(userModel);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserModel login){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        Authentication authenticate =
                this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserModel user = (UserModel) authenticate.getPrincipal();
        UserDTO response = new UserDTO(user.getId(), user.getUsername(), user.getName(), tokenService.generateToken(user), user.getRoles());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
