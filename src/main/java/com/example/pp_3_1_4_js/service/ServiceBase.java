package com.example.pp_3_1_4_js.service;


import com.example.pp_3_1_4_js.model.Role;
import com.example.pp_3_1_4_js.model.User;
import com.example.pp_3_1_4_js.repository.RoleRepository;
import com.example.pp_3_1_4_js.repository.UserRepository;
import com.example.pp_3_1_4_js.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;


@Component
public class ServiceBase {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public ServiceBase(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }


    @PostConstruct
    private void startDB() {
        Role userRole = new Role("ROLE_USER");
        Role adminRole = new Role("ROLE_ADMIN");
        roleRepository.save(userRole);
        roleRepository.save(adminRole);
        Set<Role> superAdminRole = new HashSet<Role>();
        superAdminRole.add(adminRole);
        superAdminRole.add(userRole);
        Set<Role> userSet = new HashSet<>();
        userSet.add(userRole);

        User admin = new User("Ivan", "Ivanov", 25, "admin@gmail.com", "admin");
        User user = new User("Oleg", "Sergeev", 35, "user@gmail.com", "user");

        user.setRoles(userSet);
        admin.setRoles(superAdminRole);
        userService.addUser(user);
        userService.addUser(admin);
    }

}
