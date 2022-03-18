package com.example.pp_3_1_4_js.controller;

import com.example.pp_3_1_4_js.model.Role;
import com.example.pp_3_1_4_js.model.User;
import com.example.pp_3_1_4_js.service.RoleService;
import com.example.pp_3_1_4_js.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api")
public class RestAdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public RestAdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") long id) {
        User user = userService.findUser(id);
        return user;
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user, @RequestParam(required = false, name = "selectedRoles") String[] selectedRoles) {
        Set<Role> addRoles = new HashSet<>();
        for (String s : selectedRoles) {
            if (s.contains("ADMIN")) {
                addRoles.add(roleService.getRoleByName("ROLE_ADMIN"));
            }
            if (s.contains("USER")) {
                addRoles.add(roleService.getRoleByName("ROLE_USER"));
            }
        }
        user.setRoles(addRoles);
        userService.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PatchMapping("/edit")
    public ResponseEntity<User> patchUser(@RequestBody User user, @RequestParam(required = false, name = "selectedRoles") String[] selectedRoles) {
        Set<Role> editRoles = new HashSet<>();
        for (String s : selectedRoles) {
            if (s.contains("ADMIN")) {
                editRoles.add(roleService.getRoleByName("ROLE_ADMIN"));
            }
            if (s.contains("USER")) {
                editRoles.add(roleService.getRoleByName("ROLE_USER"));
            }
        }
        user.setRoles(editRoles);
        userService.editUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam(required = true, name = "deleteId") Long id) {
        userService.deleteUser(id);
    }


    @GetMapping("/thisUser")
    @ResponseBody
    public ResponseEntity<User> currentClient(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(userService.findUser(user.getId()), HttpStatus.OK);
    }

    @GetMapping("/userInfo")
    @ResponseBody
    public String roles(@AuthenticationPrincipal User user) {
        String roles = new String();
        for (Role r : user.getRoles()) {
            roles += r.toString() + " ";
        }
        return roles;
    }
}
