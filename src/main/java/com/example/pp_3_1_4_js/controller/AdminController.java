package com.example.pp_3_1_4_js.controller;


import com.example.pp_3_1_4_js.model.User;
import com.example.pp_3_1_4_js.service.RoleService;
import com.example.pp_3_1_4_js.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/admin")
    public String adminPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        User user = userService.getUserByUsername(userDetails.getUsername());
        model.addAttribute("user", user);

        User admin = userService.getUserByUsername(userDetails.getUsername());
        model.addAttribute("admin", admin);


        model.addAttribute("users", userService.getAllUser());
        model.addAttribute("newUser", new User());
        model.addAttribute("roleList", roleService.getAllRoles());

        return "admin";
    }

    @PostMapping(value = "/admin/add")
    public String addUser(@ModelAttribute User newUser,
                          @RequestParam(value = "checked", required = false) Long[] checked) {
        if (checked == null) {
            newUser.setOneRole(roleService.getRoleByRole("USER"));
        } else {
            for (Long aLong : checked) {
                if (aLong != null) {
                    newUser.setOneRole(roleService.getRoleByID(aLong));
                }
            }
        }
        userService.addUser(newUser);
        return "redirect:/admin";
    }

    @PatchMapping(value = "/admin/edit/{id}")
    public String updateUser(@ModelAttribute User user,
                             @RequestParam(value = "checked", required = false) Long[] checked) {
        if (checked == null) {
            user.setOneRole(roleService.getRoleByRole("USER"));
            userService.updateUser(user);
        } else {
            for (Long aLong : checked) {
                if (aLong != null) {
                    user.setOneRole(roleService.getRoleByID(aLong));
                    userService.updateUser(user);
                }
            }
        }
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/delete/{id}")
    public String getUserId(@PathVariable(value = "id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

}
