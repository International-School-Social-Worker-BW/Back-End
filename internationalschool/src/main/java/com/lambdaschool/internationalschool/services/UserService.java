package com.lambdaschool.internationalschool.services;

import com.lambdaschool.internationalschool.models.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService
{
    UserDetails loadUserByUsername(String username);

    List<User> findAll();

    User findUserById(long id);

    User findByEmail(String email);

    void delete(long id);

    User save(User user);

    User update(User user, long id, boolean isAdmin);

    void deleteUserRole(long userid, long roleid);

    void addUserRole(long userid, long roleid);
}