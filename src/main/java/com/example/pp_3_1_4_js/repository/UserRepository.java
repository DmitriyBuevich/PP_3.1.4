package com.example.pp_3_1_4_js.repository;

import com.example.pp_3_1_4_js.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM  User u join fetch u.roles where u.email = :email")
    User findUserByEmail(String email);
}
