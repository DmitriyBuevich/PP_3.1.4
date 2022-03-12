package com.example.pp_3_1_4_js.controller;

import com.example.pp_3_1_4_js.exeption.NoSuchUserException;
import com.example.pp_3_1_4_js.exeption.UserIncorrectData;
import com.example.pp_3_1_4_js.model.Role;
import com.example.pp_3_1_4_js.model.User;
import com.example.pp_3_1_4_js.service.RoleService;
import com.example.pp_3_1_4_js.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/admin")
public class RestAdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public RestAdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public List<User> showUsers() {
        List<User> allUsers = userService.getAllUser();
        return allUsers;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);

        if (user == null) {
            throw new NoSuchUserException("There is no user with ID = " +
                    id + " in Database");
        }
        return user;
    }


//    @PostMapping
//    public ResponseEntity<User> createUser (@RequestBody User user,
//                                       @RequestParam(required = false, name = "newRoles") String[] newRoles) {
//        for (String role : newRoles) {
//            user.setOneRole(roleService.getRoleByRole(role));
//        }
//        userService.addUser(user);
//        return ResponseEntity.ok().body(user);
//    }

    @PostMapping
    public User addNewUser(@RequestBody User user){
        userService.addUser(user);

        return user;
    }

//    @PostMapping
//    public User addNewUser(@RequestBody User user) {
//        userService.addUser(user);
//        user.setOneRole(roleService.getRoleByRole("ROLE_USER"));
//
//        return user;
//    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        userService.updateUser(user);

        return user;
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return "User with ID = " + id + " deleted!";
    }

}
