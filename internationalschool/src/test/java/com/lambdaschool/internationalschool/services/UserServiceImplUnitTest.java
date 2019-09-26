package com.lambdaschool.internationalschool.services;

import com.lambdaschool.internationalschool.InternationalSchoolApplication;
import com.lambdaschool.internationalschool.exceptions.ResourceFoundException;
import com.lambdaschool.internationalschool.exceptions.ResourceNotFoundException;
import com.lambdaschool.internationalschool.models.Role;
import com.lambdaschool.internationalschool.models.User;
import com.lambdaschool.internationalschool.models.UserRoles;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * I am testing UserServiceImpl so want 100% in UserServiceImpl
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InternationalSchoolApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplUnitTest
{
    @Autowired
    private UserService userService;

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
    public void A_loadUserByUsername()
    {
        assertEquals("jon@jon.com", userService.loadUserByUsername("jon@jon.com").getUsername());
    }

    @Test (expected = UsernameNotFoundException.class)
    public void AA_loadUserByUsernameNotfound()
    {
        assertEquals("jon@jon.com", userService.loadUserByUsername("turtle").getUsername());
    }


    @Test
    public void B_findUserById()
    {
        assertEquals("jon@jon.com", userService.findUserById(3).getUseremail());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void BA_findUserByIdNotFound()
    {
        assertEquals("jon@jon.com", userService.findUserById(10).getUseremail());
    }

    @Test
    public void C_findAll()
    {
        assertEquals(5, userService.findAll().size());
    }

    @Test
    public void D_delete()
    {
        userService.delete(7);
        assertEquals(4, userService.findAll().size());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void DA_notFoundDelete()
    {
        userService.delete(100);
        assertEquals(4, userService.findAll().size());
    }

    @Test
    public void E_findByUsername()
    {
        assertEquals("jon@jon.com", userService.findByEmail("jon@jon.com").getUseremail());
    }

    @Test (expected = ResourceNotFoundException.class)
    public void AA_findByUsernameNotfound()
    {
        assertEquals("jon@jon.com", userService.findByEmail("turtle").getUseremail());
    }

    @Test
    public void F_save()
    {
        ArrayList<UserRoles> datas = new ArrayList<>();
        User u2 = new User("tiger", "tails", "tiger@tiger.local", "6666666666", "ILuvMath!", "The Jungle", datas);

        User saveU2 = userService.save(u2);

        System.out.println("*** DATA ***");
        System.out.println(saveU2);
        System.out.println("*** DATA ***");

        assertEquals("tiger@tiger.local", saveU2.getUseremail());
    }

    @Test (expected = ResourceFoundException.class)
    public void FA_saveResourceFound()
    {
        ArrayList<UserRoles> datas = new ArrayList<>();
        User u2 = new User("cinnamon", "suger", "jon@jon.com", "5555555555", "ILuvMath!", "A Cereal Bowl", datas);

        User saveU2 = userService.save(u2);

        System.out.println("*** DATA ***");
        System.out.println(saveU2);
        System.out.println("*** DATA ***");

        assertEquals("jon@jon.com", saveU2.getUseremail());
    }

    @Transactional
    @WithUserDetails("jon@jon.com")
    @Test
    public void G_update()
    {
        ArrayList<UserRoles> datas = new ArrayList<>();
        User u2 = new User("cinnamon", "sugar", "bunny@email.thump", "5555555555", "ILuvMath!", "A Cereal Bowl", datas);

        User updatedu2 = userService.update(u2, 3, true);

        System.out.println("*** DATA ***");
        System.out.println(updatedu2);
        System.out.println("*** DATA ***");

        assertEquals("bunny@email.thump", updatedu2.getUseremail());
    }

    @Transactional
    @WithUserDetails("jon@jon.com")
    @Test (expected = ResourceFoundException.class)
    public void GA_updateWithUserRole()
    {
        Role r2 = new Role("user");

        ArrayList<UserRoles> datas = new ArrayList<>();
        User u2 = new User("cinnamon", "suger", "rabbit@email.thump", "5555555555", "ILuvMath!", "A Cereal Bowl", datas);
        datas.add(new UserRoles(u2, r2));

        User updatedu2 = userService.update(u2, 3, false);

        System.out.println("*** DATA ***");
        System.out.println(updatedu2);
        System.out.println("*** DATA ***");

        int checking = updatedu2.getUserroles()
                                .size() - 1;
        assertEquals("user", updatedu2.getUserroles().get(checking));
    }

    @Transactional
    @WithUserDetails("bob@bob.com")
    @Test (expected = ResourceNotFoundException.class)
    public void GB_updateNotCurrentUserNorAdmin()
    {
        Role r2 = new Role("user");

        ArrayList<UserRoles> datas = new ArrayList<>();
        User u2 = new User("cinnamon", "suger", "bunny@email.thump", "5555555555", "ILuvMath!", "A Cereal Bowl", datas);

        User updatedu2 = userService.update(u2, 8, false);

        System.out.println("*** DATA ***");
        System.out.println(updatedu2);
        System.out.println("*** DATA ***");

        assertEquals("bunny@email.thump", updatedu2.getUseremail());
    }

    @Test (expected = ResourceNotFoundException.class)
    public void H_deleteUserRoleComboNotFound()
    {
        // testing cinnamon and user roles
        userService.deleteUserRole(11, 2);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void HA_deleteUserRoleRoleNotFound()
    {
        userService.deleteUserRole(7, 50);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void HB_deleteUserRoleUserNotFound()
    {
        userService.deleteUserRole(50, 2);
    }

    @Test(expected = ResourceFoundException.class)
    public void IA_addUserRoleUserRoleFound()
    {
        userService.addUserRole(3, 1);
    }

    @Test
    public void IB_deleteUserRole()
    {
        userService.deleteUserRole(6, 2);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void IC_addUserRoleRoleNotFound()
    {
        userService.addUserRole(7, 50);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void ID_addUserRoleUserNotFound()
    {
        userService.addUserRole(50, 2);
    }

    @Test
    public void IE_addUserRole()
    {
        userService.addUserRole(6, 1);
    }
}