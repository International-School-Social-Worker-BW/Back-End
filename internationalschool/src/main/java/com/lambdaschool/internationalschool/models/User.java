package com.lambdaschool.internationalschool.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// User is considered the parent entity

@Entity
@Table(name = "users")
public class User extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userid;

    @Column(nullable = false)
    private String userfirstname;

    @Column(nullable = false)
    private String userlastname;

    @Column(nullable = false, unique = true)
    private String useremail;

    @Column(nullable = false)
    private String userphone;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String organization;

    @OneToMany(mappedBy = "user",
               cascade = CascadeType.ALL)
    @JsonIgnoreProperties("user")
    private List<UserRoles> userroles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Visit> visits = new ArrayList<>();

    public User()
    {
    }

    public User(String userfirstname, String userlastname, String useremail, String userphone, String password, String organization, List<UserRoles> userRoles)
    {
        this.userfirstname = userfirstname;
        this.userlastname = userlastname;
        this.userphone = userphone;
        setUseremail(useremail);
        setPassword(password);
        this.organization = organization;
        for (UserRoles ur : userRoles)
        {
            ur.setUser(this);
        }
        this.userroles = userRoles;
    }

    public long getUserid()
    {
        return userid;
    }

    public void setUserid(long userid)
    {
        this.userid = userid;
    }

    public String getUserfirstname()
    {
        return userfirstname;
    }

    public void setUserfirstname(String userfirstname)
    {
        this.userfirstname = userfirstname;
    }

    public String getUserlastname()
    {
        return userlastname;
    }

    public void setUserlastname(String userlastname)
    {
        this.userlastname = userlastname;
    }

    public String getUseremail()
    {
        return useremail;
    }

    public void setUseremail(String useremail)
    {
        this.useremail = useremail;
    }

    public String getUserphone()
    {
        return userphone;
    }

    public void setUserphone(String userphone)
    {
        this.userphone = userphone;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public void setPasswordNoEncrypt(String password)
    {
        this.password = password;
    }

    public String getOrganization()
    {
        return organization;
    }

    public void setOrganization(String organization)
    {
        this.organization = organization;
    }

    public List<UserRoles> getUserroles()
    {
        return userroles;
    }

    public void setUserroles(List<UserRoles> userroles)
    {
        this.userroles = userroles;
    }

    public List<Visit> getVisits()
    {
        return visits;
    }

    public void setVisits(List<Visit> visits)
    {
        this.visits = visits;
    }

    public List<SimpleGrantedAuthority> getAuthority()
    {
        List<SimpleGrantedAuthority> rtnList = new ArrayList<>();

        for (UserRoles r : this.userroles)
        {
            String myRole = "ROLE_" + r.getRole()
                                       .getName()
                                       .toUpperCase();
            rtnList.add(new SimpleGrantedAuthority(myRole));
        }

        return rtnList;
    }

    @Override
    public String toString()
    {
        return "User{" + "userid=" + userid + ", userfirstname='" + userfirstname + '\'' + ", userlastname='" + userlastname + '\'' + ", useremail='" + useremail + '\'' + ", userphone='" + userphone + '\'' + ", password='" + password + '\'' + ", organization='" + organization + '\'' + ", userroles=" + userroles + ", visits=" + visits + '}';
    }
}
