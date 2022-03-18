package com.example.pp_3_1_4_js.service;

import com.example.pp_3_1_4_js.model.Role;
import com.example.pp_3_1_4_js.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRoleById(Long id) {
        return roleRepository.getById(id);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public void addRole(Role role) {
        roleRepository.save(role);
    }

    public Role getRoleByName(String name) {
        return roleRepository.getRoleByName(name);
    }


}


