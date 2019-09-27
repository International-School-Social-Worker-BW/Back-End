package com.lambdaschool.internationalschool.services;

import com.lambdaschool.internationalschool.InternationalSchoolApplication;
import com.lambdaschool.internationalschool.models.Visit;
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

import java.util.Date;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InternationalSchoolApplication.class)
public class VisitServiceImplUnitTest
{

    @Autowired
    VisitService visitService;

    @Autowired
    StudentService studentService;

    @Autowired
    UserService userService;

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
    public void A_FindAll()
    {
        assertEquals(1, visitService.findAll().size());
    }

    @Transactional
    @WithUserDetails("jon@jon.com")
    @Test
    public void B_save()
    {
        Visit v1 = new Visit();
        v1.setVisits(new Date());
        Visit visit = visitService.save(v1, 8, 3);

        assertEquals("Anna", visit.getStudent().getStudentfirstname());
    }

    @Transactional
    @WithUserDetails("jon@jon.com")
    @Test
    public void C_delete()
    {
        visitService.delete(11);
        assertEquals(0, visitService.findAll().size());
    }
}