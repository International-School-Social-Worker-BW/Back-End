package com.lambdaschool.internationalschool.services;

import com.lambdaschool.internationalschool.exceptions.ResourceFoundException;
import com.lambdaschool.internationalschool.exceptions.ResourceNotFoundException;
import com.lambdaschool.internationalschool.models.Role;
import com.lambdaschool.internationalschool.models.User;
import com.lambdaschool.internationalschool.models.UserRoles;
import com.lambdaschool.internationalschool.repository.RoleRepository;
import com.lambdaschool.internationalschool.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService
{

    @Autowired
    private UserRepository userrepos;

    @Autowired
    private RoleRepository rolerepos;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String useremail) throws UsernameNotFoundException
    {
        User user = userrepos.findByUseremail(useremail);
        if (user == null)
        {
            throw new UsernameNotFoundException("Invalid email or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUseremail(), user.getPassword(), user.getAuthority());
    }

    public User findUserById(long id) throws ResourceNotFoundException
    {
        return userrepos.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("User id " + id + " not found!"));
    }

    @Override
    public List<User> findAll()
    {
        List<User> list = new ArrayList<>();
        userrepos.findAll()
                 .iterator()
                 .forEachRemaining(list::add);
        return list;
    }

    @Transactional
    @Override
    public void delete(long id)
    {
        userrepos.findById(id)
                 .orElseThrow(() -> new ResourceNotFoundException("User id " + id + " not found!"));
        userrepos.deleteById(id);
    }

    @Override
    public User findByEmail(String email)
    {
        User uu = userrepos.findByUseremail(email);
        if (uu == null)
        {
            throw new ResourceNotFoundException("User email " + email + " not found!");
        }
        return uu;
    }

    @Transactional
    @Override
    public User save(User user)
    {
        if (userrepos.findByUseremail(user.getUseremail()) != null)
        {
            throw new ResourceFoundException(user.getUseremail() + " is already taken!");
        }

        User newUser = new User();
        newUser.setUserfirstname(user.getUserfirstname());
        newUser.setUserlastname(user.getUserlastname());
        newUser.setUseremail(user.getUseremail());
        newUser.setUserphone(user.getUserphone());
        newUser.setPasswordNoEncrypt(user.getPassword());

        ArrayList<UserRoles> newRoles = new ArrayList<>();
        for (UserRoles ur : user.getUserroles())
        {
            long id = ur.getRole()
                        .getRoleid();
            Role role = rolerepos.findById(id)
                                 .orElseThrow(() -> new ResourceNotFoundException("Role id " + id + " not found!"));
            newRoles.add(new UserRoles(newUser, ur.getRole()));
        }
        newUser.setUserroles(newRoles);

        return userrepos.save(newUser);
    }


    @Transactional
    @Override
    public User update(User user, long id, boolean isAdmin)
    {
        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        User currentUser = userrepos.findByUseremail(authentication.getName());

        if (id == currentUser.getUserid() || isAdmin)
        {
            if (user.getUserfirstname() != null)
            {
                currentUser.setUserfirstname(user.getUserfirstname());
            }
            if (user.getUserlastname() != null)
            {
                currentUser.setUserlastname(user.getUserlastname());
            }
            if (user.getUseremail() != null)
            {
                currentUser.setUseremail(user.getUseremail());
            }
            if (user.getUserphone() != null)
            {
                currentUser.setUserphone(user.getUserphone());
            }

            if (user.getPassword() != null)
            {
                currentUser.setPasswordNoEncrypt(user.getPassword());
            }

            if (user.getUserroles()
                    .size() > 0)
            {
                throw new ResourceFoundException("User Roles are not updated through User");
            }

            return userrepos.save(currentUser);
        } else
        {
            throw new ResourceNotFoundException(id + " Not current user");
        }
    }

    @Transactional
    @Override
    public void deleteUserRole(long userid, long roleid)
    {
        userrepos.findById(userid)
                 .orElseThrow(() -> new ResourceNotFoundException("User id " + userid + " not found!"));
        rolerepos.findById(roleid)
                 .orElseThrow(() -> new ResourceNotFoundException("Role id " + roleid + " not found!"));

        if (rolerepos.checkUserRolesCombo(userid, roleid)
                     .getCount() > 0)
        {
            rolerepos.deleteUserRoles(userid, roleid);
        } else
        {
            throw new ResourceNotFoundException("Role and User Combination Does Not Exists");
        }
    }

    @Transactional
    @Override
    public void addUserRole(long userid, long roleid)
    {
        userrepos.findById(userid)
                 .orElseThrow(() -> new ResourceNotFoundException("User id " + userid + " not found!"));
        rolerepos.findById(roleid)
                 .orElseThrow(() -> new ResourceNotFoundException("Role id " + roleid + " not found!"));

        if (rolerepos.checkUserRolesCombo(userid, roleid)
                     .getCount() <= 0)
        {
            rolerepos.insertUserRoles(userid, roleid);
        } else
        {
            throw new ResourceFoundException("Role and User Combination Already Exists");
        }
    }
}
