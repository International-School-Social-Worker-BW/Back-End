package com.lambdaschool.internationalschool.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.internationalschool.models.Role;
import com.lambdaschool.internationalschool.models.Student;
import com.lambdaschool.internationalschool.models.User;
import com.lambdaschool.internationalschool.models.UserRoles;
import com.lambdaschool.internationalschool.services.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// mocking service to test controller

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, secure = false)
public class UserControllerUnitTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private List<User> userList;

    @Before
    public void setUp() throws Exception
    {
        userList = new ArrayList<>();

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
        Student s1 = new Student("Anna", "Smith", 8, 3, "current", true, true, false, "Marie Smith", "Mother", "4444444444", "'marie@marie.com", "'Truth is dead,' says Marx. The rubicon, and hence the failure, of neocultural nihilism prevalent in Rushdie’s The Ground Beneath Her Feetemerges again in The Moor’s Last Sigh, although in a more mythopoeticalsense.", "Hubbard[1] suggests that we have to choose between neostructural discourse and the textual paradigm of reality. In a sense, thesubject is interpolated into a Lyotardist narrative that includes art as a reality.");
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
        userService.save(u4);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
        User u5 = new User("jane", "doe", "jane@jane.com", "333-333-3333", "password", "School 1", users);
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
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void listAllUsers() throws Exception
    {
        String apiUrl = "/users/users";

        Mockito.when(userService.findAll()).thenReturn(userList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);

        // the following actually performs a real controller call
        MvcResult r = mockMvc.perform(rb).andReturn(); // this could throw an exception
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(userList);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List", er, tr);
    }

    @Test
    public void getUserById() throws Exception
    {
        String apiUrl = "/users/user/102";

        Mockito.when(userService.findUserById(102)).thenReturn(userList.get(1));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn(); // this could throw an exception
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(userList.get(1));

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List", er, tr);
    }

    @Test
    public void getUserByIdNotFound() throws Exception
    {
        String apiUrl = "/users/user/77";

        Mockito.when(userService.findUserById(77)).thenReturn(null);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn(); // this could throw an exception
        String tr = r.getResponse().getContentAsString();

        String er = "";

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List", er, tr);
    }

//    @Test
//    public void getUserByName() throws Exception
//    {
//        String apiUrl = "/users/user/name/testing";
//
//        Mockito.when(userService.findByEmail("jon@jon.com")).thenReturn(userList.get(0));
//
//        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
//        MvcResult r = mockMvc.perform(rb).andReturn(); // this could throw an exception
//        String tr = r.getResponse().getContentAsString();
//
//        ObjectMapper mapper = new ObjectMapper();
//        String er = mapper.writeValueAsString(userList.get(0));
//
//        System.out.println("Expect: " + er);
//        System.out.println("Actual: " + tr);
//
//        assertEquals("Rest API Returns List", er, tr);
//    }

    @Test
    public void getCurrentUserName() throws Exception
    {
        // requires security which we have turned off for unit test
        // refer to integration testing for test of this method
    }

    @Test
    public void addNewUser() throws Exception
    {
        String apiUrl = "/users/user";

        // build a user
        ArrayList<UserRoles> thisRole = new ArrayList<>();
        User u1 = new User();
        u1.setUserid(100);
        u1.setUseremail("tiger@tiger.com");
        u1.setPassword("ILuvM4th!");
        u1.setUserroles(thisRole);


        ObjectMapper mapper = new ObjectMapper();
        String userString = mapper.writeValueAsString(u1);

        Mockito.when(userService.save(any(User.class))).thenReturn(u1);

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
                                                  .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                                  .content(userString);

        mockMvc.perform(rb).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateUser() throws Exception
    {
        String apiUrl = "/users/user/{userid}";

        // build a user
        ArrayList<UserRoles> thisRole = new ArrayList<>();
        User u1 = new User();
        u1.setUserid(100);
        u1.setUserfirstname("tigerUpdated");
        u1.setPassword("ILuvM4th!");

        Mockito.when(userService.update(u1, 100L, true)).thenReturn(u1);

        ObjectMapper mapper = new ObjectMapper();
        String userString = mapper.writeValueAsString(u1);

        RequestBuilder rb = MockMvcRequestBuilders.put(apiUrl, 100L)
                                                  .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                                  .content(userString);

        mockMvc.perform(rb).andExpect(status().is2xxSuccessful()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteUserById()throws Exception
    {
        String apiUrl = "/users/user/{userid}";

        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl, "3")
                                                  .contentType(MediaType.APPLICATION_JSON)
                                                  .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(rb)
               .andExpect(status().is2xxSuccessful())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteUserRoleByIds() throws Exception
    {
        String apiUrl = "/users/user/{userid}/role/{roleid}";

        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl, 3, 2)
                                                  .contentType(MediaType.APPLICATION_JSON)
                                                  .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(rb)
               .andExpect(status().is2xxSuccessful())
               .andDo(MockMvcResultHandlers.print());
    }

    // @PostMapping("/user/{userid}/role/{roleid}")
    // userService.addUserRole(userid, roleid);
    @Test
    public void postUserRoleByIds()
    {
    }
}