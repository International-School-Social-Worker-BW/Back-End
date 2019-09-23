package com.lambdaschool.internationalschool.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "visits")
public class Visit
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long visitid;
    private Date visits;

    @ManyToOne
    @JoinColumn(name = "studentid")
    @JsonIgnoreProperties("visits")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties("visits")
    private  User user;

    public Visit()
    {
    }

    public Visit(Date visits, Student student, User user)
    {
        this.visits = visits;
        this.student = student;
        this.user = user;
    }

    public long getVisitid()
    {
        return visitid;
    }

    public void setVisitid(long visitid)
    {
        this.visitid = visitid;
    }

    public Date getVisits()
    {
        return visits;
    }

    public void setVisits(Date visits)
    {
        this.visits = visits;
    }

    public Student getStudent()
    {
        return student;
    }

    public void setStudent(Student student)
    {
        this.student = student;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}
