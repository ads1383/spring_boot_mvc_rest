package com.denisakulov.springboot.spring_boot_mvc.dao;

import com.denisakulov.springboot.spring_boot_mvc.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {}
