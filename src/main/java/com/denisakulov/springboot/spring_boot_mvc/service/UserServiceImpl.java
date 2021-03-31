package com.denisakulov.springboot.spring_boot_mvc.service;


import com.denisakulov.springboot.spring_boot_mvc.dao.RoleRepository;
import com.denisakulov.springboot.spring_boot_mvc.dao.UserRepository;
import com.denisakulov.springboot.spring_boot_mvc.model.AuthenticationProviderElse;
import com.denisakulov.springboot.spring_boot_mvc.model.Role;
import com.denisakulov.springboot.spring_boot_mvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                                                                        user.getPassword(),
                                                                        mapRolesToauthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToauthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getRole())).collect(Collectors.toList());
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<Role> listRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public Long delete(Long id) {
        User user = getById(id);
        if(user != null) {
            userRepository.delete(user);
            return id;
        } else {
            return null;
        }
    }


    @Override
    public Set<Role> hashSetRolesByListId(List<Long> rolesId) {

        return rolesId.stream()
                .map(p -> roleRepository.findById(p).orElse(null))
                .filter(value -> value != null)
                .collect(Collectors.toSet());
    }

    @Override
    public User findByPrincipal(Principal principal) {
        OAuth2AuthenticationToken oAuth2Authentication = (OAuth2AuthenticationToken) principal;
        Map<String, Object> map = oAuth2Authentication.getPrincipal().getAttributes();
        String username = (String) map.get("email");
        User userGetBase = findByUsername(username);
        if (userGetBase == null) {
            User userOath = new User();
            userOath.setFirstName((String) map.get("given_name"));
            userOath.setLastName((String) map.get("family_name"));
            userOath.setUsername((String) map.get("email"));
            Set<Role> roles = new HashSet<>();
            Role role= new Role(2L, "ROLE_USER");
            roles.add(role);
            userOath.setRoles(roles);
            userOath.setAge(0);
            userOath.setAuthProvider("GOOGLE");
            userOath.setEnabled(true);
            save(userOath);
            return userOath;

        } else {
            userGetBase.setFirstName((String) map.get("given_name"));
            userGetBase.setLastName((String) map.get("family_name"));
            userGetBase.setUsername((String) map.get("email"));
            userGetBase.setAuthProvider("GOOGLE");
            userGetBase.setEnabled(true);
            save(userGetBase);
           return userGetBase;
        }
    }
}
