package com.lambdaschool.internationalschool.services;

import com.lambdaschool.internationalschool.InternationalSchoolApplication;
import com.lambdaschool.internationalschool.models.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InternationalSchoolApplication.class)
public class StudentServiceImplTest
{

    @Autowired
    StudentService studentService;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void A_findAll()
    {
        assertEquals(3, studentService.findAll().size());
    }

    @Test
    public void B_FindStudentById()
    {
        assertEquals("Anna", studentService.findStudentById(8).getStudentfirstname());
    }

    @Transactional
    @WithUserDetails("jon@jon.com")
    @Test
    public void C_save()
    {
        Student s2 = new Student("Frank", "Jones", 7, 2, "current", true, true, false, "Tom Jones", "Father", "4444444444", "'marie@marie.com", "'The rubicon, and hence the failure, of neocultural nihilism prevalent in Rushdie’s The Ground Beneath Her Feet", "In a sense, thesubject is interpolated into a Lyotardist narrative that includes art as a reality.");
        Student saveS2 = studentService.save(s2);

        assertEquals("Frank", saveS2.getStudentfirstname());
    }

    @Transactional
    @WithUserDetails("jon@jon.com")
    @Test
    public void D_update()
    {
        Student s2 = new Student("George", "Jones", 7, 2, "current", true, true, false, "Tom Jones", "Father", "4444444444", "'marie@marie.com", "'The rubicon, and hence the failure, of neocultural nihilism prevalent in Rushdie’s The Ground Beneath Her Feet", "In a sense, thesubject is interpolated into a Lyotardist narrative that includes art as a reality.");

    }

    @Transactional
    @WithUserDetails("jon@jon.com")
    @Test
    public void E_delete()
    {
        studentService.delete(10);
        assertEquals(2, studentService.findAll().size());
    }
}