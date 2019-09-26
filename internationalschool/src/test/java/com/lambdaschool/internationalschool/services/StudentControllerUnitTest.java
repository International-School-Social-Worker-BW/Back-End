package com.lambdaschool.internationalschool.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.internationalschool.controllers.StudentController;
import com.lambdaschool.internationalschool.models.Student;
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

@RunWith(SpringRunner.class)
@WebMvcTest(value = StudentController.class, secure = false)
public class StudentControllerUnitTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    private List<Student> studentList;

    @Before
    public void setUp() throws Exception
    {
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

    }

    @After
    public void tearDown() throws Exception
    {

    }

    @Test
    public void listAllStudents() throws Exception
    {
        String apiUrl = "/students/students";

        Mockito.when(studentService.findAll()).thenReturn(studentList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);

        // the following actually performs a real controller call
        MvcResult r = mockMvc.perform(rb).andReturn(); // this could throw an exception
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(studentList);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List", er, tr);


    }

    @Test
    public void findStudentById() throws Exception
    {
        String apiUrl ="/students/student/111";

        Mockito.when(studentService.findStudentById(111)).thenReturn(studentList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn(); // this could throw an exception
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(studentList.get(0));

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List", er, tr);
    }

    @Test
    public void findStudentByIdNotFound() throws Exception
    {
        String apiUrl ="/students/student/200";

        Mockito.when(studentService.findStudentById(200)).thenReturn(null);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn(); // this could throw an exception
        String tr = r.getResponse().getContentAsString();

        String er = "";

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List", er, tr);
    }

    @Test
    public void addNewStudent() throws Exception
    {
        String apiUrl = "/students/new";
        Student s3 = new Student("Kyle", "Simmons", 6, 1, "current", true, true, false, "Kelly Simmons", "Mother", "1234567890", "'kelly@kelly.com", "'Truth is dead,' says Marx. The rubicon, and hence the failure, of neocultural nihilism prevalent in Rushdie’s The Ground Beneath Her Feetemerges again in The Moor’s Last Sigh, although in a more mythopoeticalsense.", "Hubbard[1] suggests that we have to choose between neostructural discourse and the textual paradigm of reality. In a sense, thesubject is interpolated into a Lyotardist narrative that includes art as a reality.");
        s3.setStudentid(114);

        ObjectMapper mapper = new ObjectMapper();
        String userString = mapper.writeValueAsString(s3);

        Mockito.when(studentService.save(any(Student.class))).thenReturn(s3);

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(userString);

        mockMvc.perform(rb).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());

    }

//    Student s3 = new Student("Abby", "Taylor", 6, 1, "current", true, true, false, "Tammy Taylor", "Mother", "1234567890", "'marie@marie.com", "'Truth is dead,' says Marx. The rubicon, and hence the failure, of neocultural nihilism prevalent in Rushdie’s The Ground Beneath Her Feetemerges again in The Moor’s Last Sigh, although in a more mythopoeticalsense.", "Hubbard[1] suggests that we have to choose between neostructural discourse and the textual paradigm of reality. In a sense, thesubject is interpolated into a Lyotardist narrative that includes art as a reality.");

    @Test
    public void updateStudent() throws Exception
    {
        String apiUrl = "/students/student/{studentid}";
        Student s3 = new Student();
        s3.setContactname("Brent Simmons");
        s3.setRelationship("Father");

        Mockito.when(studentService.update(s3, 114L)).thenReturn(s3);

        ObjectMapper mapper = new ObjectMapper();
        String userString = mapper.writeValueAsString(s3);

        RequestBuilder rb = MockMvcRequestBuilders.put(apiUrl, 114L)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(userString);

        mockMvc.perform(rb).andExpect(status().is2xxSuccessful()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteStudent() throws Exception
    {
        String apiUrl = "/students/student/{userid}";

        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl, "113")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(rb)
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

}
