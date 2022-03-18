package com.example.pp_3_1_4_js.controller;


import com.example.pp_3_1_4_js.model.User;
import com.example.pp_3_1_4_js.service.RoleService;
import com.example.pp_3_1_4_js.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String allUsers(@AuthenticationPrincipal User admin, ModelMap model, User addUser){
        List<User> userList = userService.getUsers();
        model.addAttribute("allUsers", userList);
        model.addAttribute("admin", admin);
        model.addAttribute("role_admin", roleService.getRoleByName("ROLE_ADMIN"));
        model.addAttribute("role_user", roleService.getRoleByName("ROLE_USER"));
        model.addAttribute("addUser", addUser);
        return "admin";

    }

    @DeleteMapping ("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String patchUser(@ModelAttribute("user") User user){
        userService.editUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("addUser") User user){
        userService.addUser(user);
        return "redirect:/admin";
    }
}