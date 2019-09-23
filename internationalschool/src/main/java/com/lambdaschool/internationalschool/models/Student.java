package com.lambdaschool.internationalschool.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class Student
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long studentid;
    private String studentfirstname;
    private String studentlastname;
    private int age;
    private int grade;
    private String status;
    private boolean birthcertificate;
    private boolean insurance;
    private boolean specialneeds;
    private String contactname;
    private String relationship;
    private String contactphone;
    private String contactemail;
    @Column(columnDefinition = "TEXT")
    private String backgroundinfo;
    @Column(columnDefinition = "TEXT")
    private String criticalinfo;
    // TODO add picture storage

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "student")
    private List<Visit> visits = new ArrayList<>();

    public Student()
    {
    }

    public Student(String studentfirstname, String studentlastname, int age, int grade, String status, boolean birthcertificate, boolean insurance, boolean specialneeds, String contactname, String relationship, String contactphone, String contactemail, String backgroundinfo, String criticalinfo)
    {
        this.studentfirstname = studentfirstname;
        this.studentlastname = studentlastname;
        this.age = age;
        this.grade = grade;
        this.status = status;
        this.birthcertificate = birthcertificate;
        this.insurance = insurance;
        this.specialneeds = specialneeds;
        this.contactname = contactname;
        this.relationship = relationship;
        this.contactphone = contactphone;
        this.contactemail = contactemail;
        this.backgroundinfo = backgroundinfo;
        this.criticalinfo = criticalinfo;
    }

    public long getStudentid()
    {
        return studentid;
    }

    public void setStudentid(long studentid)
    {
        this.studentid = studentid;
    }

    public String getStudentfirstname()
    {
        return studentfirstname;
    }

    public void setStudentfirstname(String studentfirstname)
    {
        this.studentfirstname = studentfirstname;
    }

    public String getStudentlastname()
    {
        return studentlastname;
    }

    public void setStudentlastname(String studentlastname)
    {
        this.studentlastname = studentlastname;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public int getGrade()
    {
        return grade;
    }

    public void setGrade(int grade)
    {
        this.grade = grade;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public boolean isBirthcertificate()
    {
        return birthcertificate;
    }

    public void setBirthcertificate(boolean birthcertificate)
    {
        this.birthcertificate = birthcertificate;
    }

    public boolean isInsurance()
    {
        return insurance;
    }

    public void setInsurance(boolean insurance)
    {
        this.insurance = insurance;
    }

    public boolean isSpecialneeds()
    {
        return specialneeds;
    }

    public void setSpecialneeds(boolean specialneeds)
    {
        this.specialneeds = specialneeds;
    }

    public String getContactname()
    {
        return contactname;
    }

    public void setContactname(String contactname)
    {
        this.contactname = contactname;
    }

    public String getRelationship()
    {
        return relationship;
    }

    public void setRelationship(String relationship)
    {
        this.relationship = relationship;
    }

    public String getContactphone()
    {
        return contactphone;
    }

    public void setContactphone(String contactphone)
    {
        this.contactphone = contactphone;
    }

    public String getContactemail()
    {
        return contactemail;
    }

    public void setContactemail(String contactemail)
    {
        this.contactemail = contactemail;
    }

    public String getBackgroundinfo()
    {
        return backgroundinfo;
    }

    public void setBackgroundinfo(String backgroundinfo)
    {
        this.backgroundinfo = backgroundinfo;
    }

    public String getCriticalinfo()
    {
        return criticalinfo;
    }

    public void setCriticalinfo(String criticalinfo)
    {
        this.criticalinfo = criticalinfo;
    }

    public List<Visit> getVisits()
    {
        return visits;
    }

    public void setVisits(List<Visit> visits)
    {
        this.visits = visits;
    }
}
