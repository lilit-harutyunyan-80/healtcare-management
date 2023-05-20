package com.example.healtcaremanagement.security;


import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;



public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private User user;
    public CurrentUser(User user) {
        super(user.getUsername(), user.getPassword(),
                AuthorityUtils.createAuthorityList(user.getUsername().toString()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
