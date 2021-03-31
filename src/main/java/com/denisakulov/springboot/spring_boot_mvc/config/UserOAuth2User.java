package com.denisakulov.springboot.spring_boot_mvc.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;


import java.util.Collection;
import java.util.Map;


public class UserOAuth2User implements OAuth2User {

    private OAuth2User oAuth2User;

    public UserOAuth2User(OAuth2User oAuth2User) {
        this.oAuth2User = oAuth2User;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oAuth2User.getAttribute("email");
    }

//    public String getTest() {
//        return oAuth2User.getAttribute("email");
//    }
}
