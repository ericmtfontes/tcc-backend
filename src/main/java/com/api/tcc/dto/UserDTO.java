package com.api.tcc.dto;

import com.api.tcc.models.RoleModel;

import java.io.Serializable;
import java.util.Set;

public class UserDTO implements Serializable {

    private Long id;
    private String username;
    private String name;
    private String token;

    private Set<RoleModel> roles;

    public UserDTO(){
    }

    public UserDTO(Long id, String username, String name, String token, Set<RoleModel> roles) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.token = token;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<RoleModel> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleModel> roles) {
        this.roles = roles;
    }
}
