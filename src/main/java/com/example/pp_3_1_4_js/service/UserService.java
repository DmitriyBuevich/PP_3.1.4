package com.example.pp_3_1_4_js.service;




import com.example.pp_3_1_4_js.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUser();
    void addUser(User user);
    void deleteById(Long id);
    User getUserById(Long id);
    void updateUser(User user);
    User getUserByUsername(String username);

}

