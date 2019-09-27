package com.lambdaschool.internationalschool.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.internationalschool.models.*;
import com.lambdaschool.internationalschool.services.StudentService;
import com.lambdaschool.internationalschool.services.UserService;
import com.lambdaschool.internationalschool.services.VisitService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = VisitsController.class, secure = false)
public class VisitsControllerUnitTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private  StudentService studentService;

    @MockBean
    VisitService visitService;

    private List<User> userList;
    private List<Student> studentList;
    private List<Visit> visitList;


    @Before
    public void setUp() throws Exception
    {

        userList = new ArrayList<>();
        studentList = new ArrayList<>();
        visitList = new ArrayList<>();

        Role r1 = new Role("admin");
        r1.setRoleid(1);
        Role r2 = new Role("user");
        r2.setRoleid(2);
        Role r3 = new Role("data");
        r3.setRoleid(3);

        // admin, user
        ArrayList<UserRoles> admins = new ArrayList<>();
        admins.add(new UserRoles(new User(), r1));
        admins.add(new UserRoles(new User(), r2));

        User u1 = new User("jon", "scott", "jon@jon.com", "123-456-7890", "password", "School 1", admins);
        u1.setUserid(101);
        userService.save(u1);

        // user
        ArrayList<UserRoles> users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));

        User u2 = new User("bob", "roberts", "bob@bob.com", "098-765-4321", "password", "School 1", users);
        u2.setUserid(102);
        userService.save(u2);

        // user
        users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
        User u3 = new User("ashley", "smith", "ash@ash.com", "111-111-1111", "qwerty", "School 1", users);
        u3.setUserid(103);
        userService.save(u3);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
        User u4 = new User("tom", "jones", "tom@tom.com", "222-222-2222", "password", "School 1", users);
        u4.setUserid(104);
        userService.save(u4);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
        User u5 = new User("jane", "doe", "jane@jane.com", "333-333-3333", "password", "School 1", users);
        u5.setUserid(105);
        userService.save(u5);

        userList.add(u1);
        userList.add(u2);
        userList.add(u3);
        userList.add(u4);
        userList.add(u5);
        System.out.println("\n*** Seed Data ***");
        for (User u : userList)
        {
            System.out.println(u);
        }
        System.out.println("*** Seed Data ***\n");

        studentList = new ArrayList<>();

        Student s1 = new Student("Anna", "Smith", 8, 3, "current", true, true, false, "Marie Smith", "Mother", "3333333333", "'marie@marie.com", "'Truth is dead,' says Marx. The rubicon, and hence the failure, of neocultural nihilism prevalent in Rushdie’s The Ground Beneath Her Feetemerges again in The Moor’s Last Sigh, although in a more mythopoeticalsense.", "Hubbard[1] suggests that we have to choose between neostructural discourse and the textual paradigm of reality. In a sense, thesubject is interpolated into a Lyotardist narrative that includes art as a reality.");
        Student s2 = new Student("Brandon", "Kelly", 8, 3, "current", true, true, false, "Hank Kelly", "Father", "4444444444", "'marie@marie.com", "'Truth is dead,' says Marx. The rubicon, and hence the failure, of neocultural nihilism prevalent in Rushdie’s The Ground Beneath Her Feetemerges again in The Moor’s Last Sigh, although in a more mythopoeticalsense.", "Hubbard[1] suggests that we have to choose between neostructural discourse and the textual paradigm of reality. In a sense, thesubject is interpolated into a Lyotardist narrative that includes art as a reality.");
        Student s3 = new Student("Abby", "Taylor", 6, 1, "current", true, true, false, "Tammy Taylor", "Mother", "1234567890", "'marie@marie.com", "'Truth is dead,' says Marx. The rubicon, and hence the failure, of neocultural nihilism prevalent in Rushdie’s The Ground Beneath Her Feetemerges again in The Moor’s Last Sigh, although in a more mythopoeticalsense.", "Hubbard[1] suggests that we have to choose between neostructural discourse and the textual paradigm of reality. In a sense, thesubject is interpolated into a Lyotardist narrative that includes art as a reality.");
        s1.setStudentid(111);
        s2.setStudentid(112);
        s3.setStudentid(113);
        studentService.save(s1);
        studentService.save(s2);
        studentService.save(s3);

        studentList.add(s1);
        studentList.add(s2);
        studentList.add(s3);

        System.out.println("\n*** Seed Data ***");
        for (Student s : studentList)
        {
            System.out.println(s);
        }
        System.out.println("*** Seed Data ***\n");

        Visit v1 = new Visit(new Date(),s1, u1);
        v1.setVisitid(201);
        v1 = visitService.save(v1, s1.getStudentid(), u1.getUserid());
        visitList.add(v1);

    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void addNewVisit() throws Exception
    {
        String apiUrl = "/visits/student/{studentid}/user/{userid}";

        // build a visit
//        User u1 = new User();
//        Student s1 = new Student();
        Visit v1 = new Visit();
        v1.setVisits(new Date());

        Mockito.when(visitService.save(v1, 112L, 105L)).thenReturn(v1);

        ObjectMapper mapper = new ObjectMapper();
        String visitString = mapper.writeValueAsString(v1);

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl, 112, 105)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(visitString);

        mockMvc.perform(rb).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteVisit() throws Exception
    {
        String apiUrl = "/visits/visit/{visitid}";

        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl, "201")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(rb)
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }
}