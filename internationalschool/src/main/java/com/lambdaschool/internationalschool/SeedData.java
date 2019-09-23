package com.lambdaschool.internationalschool;

import com.lambdaschool.internationalschool.models.*;
import com.lambdaschool.internationalschool.services.RoleService;
import com.lambdaschool.internationalschool.services.StudentService;
import com.lambdaschool.internationalschool.services.UserService;
import com.lambdaschool.internationalschool.services.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;

@Transactional
@Component
public class SeedData implements CommandLineRunner
{
    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    @Autowired
    StudentService studentService;

    @Autowired
    VisitService visitService;


    @Override
    public void run(String[] args) throws Exception
    {
        Role r1 = new Role("admin");
        Role r2 = new Role("user");
//        Role r3 = new Role("data");

        roleService.save(r1);
        roleService.save(r2);
//        roleService.save(r3);

        // admin, data, user
        ArrayList<UserRoles> admins = new ArrayList<>();
        admins.add(new UserRoles(new User(), r1));
        admins.add(new UserRoles(new User(), r2));
//        admins.add(new UserRoles(new User(), r3));
        User u1 = new User("jon", "scott", "jon@jon.com", "123-456-7890", "password", "School 1", admins);
        Student s1 = new Student("Anna", "Smith", 8, 3, "current", true, true, false, "Marie Smith", "Mother", "4444444444", "'marie@marie.com", "'Truth is dead,' says Marx. The rubicon, and hence the failure, of neocultural nihilism prevalent in Rushdie’s The Ground Beneath Her Feetemerges again in The Moor’s Last Sigh, although in a more mythopoeticalsense.", "Hubbard[1] suggests that we have to choose between neostructural discourse and the textual paradigm of reality. In a sense, thesubject is interpolated into a Lyotardist narrative that includes art as a reality.");
        userService.save(u1);
        studentService.save(s1);
//        Visit v1 = new Visit(new Date(),s1, u1);
//        s1.setStudentid(4);
//        studentService.update(s1);
//        u1.setUserid(4);
//        userService.update(u1);
//        visitService.save(v1, 4, 4);
//        s1.getVisits().add(v1);
//        u1.getVisits().add(v1);

        // data, user
//        ArrayList<UserRoles> datas = new ArrayList<>();
        ArrayList<UserRoles> users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
//        users.add(new UserRoles(new User(), r3));
        User u2 = new User("bob", "roberts", "bob@bob.com", "098-765-4321", "password", "School 1", users);
        userService.save(u2);

        // user
        users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
        User u3 = new User("ashley", "smith", "ash@ash.com", "111-111-1111", "qwerty", "School 1", users);
        userService.save(u3);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
        User u4 = new User("tom", "jones", "tom@tom.com", "222-222-2222", "password", "School 1", users);
        userService.save(u4);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
        User u5 = new User("jane", "doe", "jane@jane.com", "333-333-3333", "password", "School 1", users);
        userService.save(u5);


//        Student s1 = new Student("Anna", "Smith", 8, 3, "current", true, true, false, "Marie Smith", "Mother", "4444444444", "'marie@marie.com", "'Truth is dead,' says Marx. The rubicon, and hence the failure, of neocultural nihilism prevalent in Rushdie’s The Ground Beneath Her Feetemerges again in The Moor’s Last Sigh, although in a more mythopoeticalsense.", "Hubbard[1] suggests that we have to choose between neostructural discourse and the textual paradigm of reality. In a sense, thesubject is interpolated into a Lyotardist narrative that includes art as a reality.");


    }
}