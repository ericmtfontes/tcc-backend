package com.api.tcc.services;

import com.api.tcc.enums.RoleEnum;
import com.api.tcc.handler.UserAlreadyExistsException;
import com.api.tcc.models.RoleModel;
import com.api.tcc.models.UserModel;
import com.api.tcc.repositories.RoleRepository;
import com.api.tcc.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public void registerNewUserAccount(UserModel userModel){
        if(userRepository.findByUsername(userModel.getUsername()).isPresent()){
            throw new UserAlreadyExistsException("User already exist");
        }
        RoleModel roleModel = roleRepository.findByRole(RoleEnum.ROLE_USER);
        Set<RoleModel> roles = new HashSet<>();
        roles.add(roleModel);
        userModel.setRoles(roles);
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        userModel.setAccountNonExpired(true);
        userModel.setAccountNonLocked(true);
        userModel.setCredentialsNonExpired(true);
        userModel.setEnabled(true);
        userRepository.save(userModel);
    }

    public UserModel findById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User id " + id + " not found"));
    }
}
