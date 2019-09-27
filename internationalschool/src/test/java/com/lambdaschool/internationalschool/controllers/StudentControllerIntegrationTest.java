package com.lambdaschool.internationalschool.controllers;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.number.OrderingComparison.lessThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentControllerIntegrationTest
{

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    public static String asJsonString(final Object obj)
    {
        try
        {
            return new ObjectMapper().writeValueAsString(obj);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    @Before
    public void setUp() throws Exception
    {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception
    {

    }

    @WithUserDetails("bob@bob.com")
    @Test
    public void A_whenMeasuredResponseTime()
    {
        given().when()
                .get("/students/students")
                .then()
                .time(lessThan(5000L));
    }

    @WithUserDetails("jon@jon.com")
    @Test
    public void B_listAllStudents() throws Exception
    {
        this.mockMvc.perform(get("/students/students"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Anna")));
    }

    @WithUserDetails("jon@jon.com")
    @Test
    public void C_findStudentById() throws Exception
    {
        this.mockMvc.perform(get("/students/student/{studentid}", 8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Anna")));
    }

    @WithUserDetails("jon@jon.com")
    @Test
    public void CA_findStudentByIdNotFound() throws Exception
    {
        this.mockMvc.perform(get("/students/student/{studentid}", 100))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("ResourceNotFoundException")));
    }

    @WithUserDetails("jon@jon.com")
    @Test
    public void D_addNewStudent() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.post("/students/new")
                .content("{\"studentfirstname\": \"Roger\", \"studentlasstname\": \"Rogers\", \"age\": 10, \"grade\": 3, \"status\": \"current\", \"birthcertificate\": true, \"insurance\": true, \"specialneeds\": false, \"contactname\": \"Ronda Rodgers\", \"relationship\": \"Mother\", \"contactphone\": \"3333333333\", \"contactemail\": \"'marie@marie.com\", \"backgroundinfo\": \"'The rubicon, and hence the failure, of neocultural nihilism prevalent in Rushdie’s The Ground Beneath Her Feet.\", \"criticalinfo\": \"In a sense, thesubject is interpolated into a Lyotardist narrative that includes art as a reality.\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.header()
                        .exists("location"));
    }
    @WithUserDetails("ash@ash.com")
    @Test
    public void DA_addNewStudentNotAuthorized() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.post("/students/new")
                .content("{\"studentfirstname\": \"Roger\", \"studentlasstname\": \"Rodney\", \"age\": 10, \"grade\": 3, \"status\": \"current\", \"birthcertificate\": true, \"insurance\": true, \"specialneeds\": false, \"contactname\": \"Ronda Rodgers\", \"relationship\": \"Mother\", \"contactphone\": \"3333333333\", \"contactemail\": \"'marie@marie.com\", \"backgroundinfo\": \"'The rubicon, and hence the failure, of neocultural nihilism prevalent in Rushdie’s The Ground Beneath Her Feet.\", \"criticalinfo\": \"In a sense, thesubject is interpolated into a Lyotardist narrative that includes art as a reality.\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @WithUserDetails("jon@jon.com")
    @Test
    public void E_deleteStudentById() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.delete("/students/student/{studentid}", 10))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @WithUserDetails("jon@jon.com")
    @Test
    public void EA_deleteStudentByIdNotFound() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.delete("/students/student/{studentid}", 100))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @WithUserDetails("ash@ash.com")
    @Test
    public void EB_deleteStudentById() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.delete("/students/student/{studentid}", 10))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @WithUserDetails("jon@jon.com")
    @Test
    public void H_UpdateStudent() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.put("/students/student/{studentid}", 8)
                .content("{\"contactphone\": \"3333333333\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithUserDetails("jon@jon.com")
    @Test
    public void HA_UpdateStudentNotFound() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.put("/students/student/{studentid}", 100)
                .content("{\"contactphone\": \"3333333333\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @WithUserDetails("ash@ash.com")
    @Test
    public void HB_UpdateStudentNotAuthorized() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.put("/students/student/{studentid}", 8)
                .content("{\"contactphone\": \"3333333333\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}

//        "age": 8,
//        "grade": 3,
//        "status": "current",
//        "birthcertificate": true,
//        "insurance": true,
//        "specialneeds": false,
//        "contactname": "Marie Smith",
//        "relationship": "Mother",
//        "contactphone": "3333333333",
//        "contactemail": "'marie@marie.com",
//        "backgroundinfo": "'The rubicon, and hence the failure, of neocultural nihilism prevalent in Rushdie’s The Ground Beneath Her Feet.",
//        "criticalinfo": "In a sense, thesubject is interpolated into a Lyotardist narrative that includes art as a reality."