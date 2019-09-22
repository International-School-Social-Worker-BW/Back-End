package com.lambdaschool.internationalschool.services;

import com.lambdaschool.internationalschool.models.Student;

import java.util.List;

public interface StudentService
{
    List<Student> findAll();

    Student findStudentById(long id);

    Student save(Student student);

    Student update(Student student, long id);

    void delete(long id);
}
