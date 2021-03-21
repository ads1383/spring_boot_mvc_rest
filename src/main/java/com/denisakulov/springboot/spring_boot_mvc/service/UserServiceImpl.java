package com.denisakulov.springboot.spring_boot_mvc.service;


import com.denisakulov.springboot.spring_boot_mvc.dao.RoleRepository;
import com.denisakulov.springboot.spring_boot_mvc.dao.UserRepository;
import com.denisakulov.springboot.spring_boot_mvc.model.Role;
import com.denisakulov.springboot.spring_boot_mvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
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


}
