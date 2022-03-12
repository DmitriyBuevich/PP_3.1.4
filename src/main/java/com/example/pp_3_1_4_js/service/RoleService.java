package com.example.pp_3_1_4_js.service;

import com.example.pp_3_1_4_js.model.Role;

import java.util.Set;

public interface RoleService {
    Set<Role> getAllRoles();
    Role getRoleByID(Long id);
    Role getRoleByRole(String role);
    void addRole(Role role);
}

