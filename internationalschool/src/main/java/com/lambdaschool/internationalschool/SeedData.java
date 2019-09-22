package com.lambdaschool.internationalschool;

import com.lambdaschool.internationalschool.models.Role;
import com.lambdaschool.internationalschool.models.User;
import com.lambdaschool.internationalschool.models.UserRoles;
import com.lambdaschool.internationalschool.services.RoleService;
import com.lambdaschool.internationalschool.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional
@Component
public class SeedData implements CommandLineRunner
{
    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;


    @Override
    public void run(String[] args) throws Exception
    {
        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("data");

        roleService.save(r1);
        roleService.save(r2);
        roleService.save(r3);

        // admin, data, user
        ArrayList<UserRoles> admins = new ArrayList<>();
        admins.add(new UserRoles(new User(), r1));
        admins.add(new UserRoles(new User(), r2));
        admins.add(new UserRoles(new User(), r3));
        User u1 = new User("jon", "scott", "jon@jon.com", "123-456-7890", "password", admins);

        userService.save(u1);

        // data, user
        ArrayList<UserRoles> datas = new ArrayList<>();
        datas.add(new UserRoles(new User(), r2));
        datas.add(new UserRoles(new User(), r3));
        User u2 = new User("bob", "roberts", "bob@bob.com", "098-765-4321", "password", datas);
        userService.save(u2);

        // user
        ArrayList<UserRoles> users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
        User u3 = new User("ashley", "smith", "ash@ash.com", "111-111-1111", "qwerty", users);
        userService.save(u3);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
        User u4 = new User("tom", "jones", "tom@tom.com", "222-222-2222", "password", users);
        userService.save(u4);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
        User u5 = new User("jane", "doe", "jane@jane.com", "333-333-3333", "password", users);
        userService.save(u5);
    }
}