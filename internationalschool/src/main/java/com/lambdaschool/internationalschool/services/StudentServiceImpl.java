package com.lambdaschool.internationalschool.services;

import com.lambdaschool.internationalschool.exceptions.ResourceNotFoundException;
import com.lambdaschool.internationalschool.models.Student;
import com.lambdaschool.internationalschool.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(value = "studentService")
public class StudentServiceImpl implements StudentService
{
    @Autowired
    StudentRepository studentRepository;

    @Override
    public List<Student> findAll()
    {
        List<Student> list = new ArrayList<>();
        studentRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Student findStudentById(long id) throws ResourceNotFoundException
    {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student ID " + id + " not found" ));
    }

    @Transactional
    @Override
    public Student save(Student student)
    {
        return studentRepository.save(student);
    }

    @Transactional
    @Override
    public Student update(Student student, long id)
    {
        Student currentStudent = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student ID " + id + " not found" ));
        if (student.getStudentfirstname() != null)
        {
            currentStudent.setStudentfirstname(student.getStudentfirstname());
        }
        if (student.getStudentlastname() != null)
        {
            currentStudent.setStudentlastname(student.getStudentlastname());
        }
        if (student.getAge() != 0)
        {
            currentStudent.setAge(student.getAge());
        }
        if (student.getGrade() != 0)
        {
            currentStudent.setGrade(student.getGrade());
        }
        if (student.isBirthcertificate() != currentStudent.isBirthcertificate())
        {
            currentStudent.setBirthcertificate(student.isBirthcertificate());
        }
        if (student.isInsurance() != currentStudent.isInsurance())
        {
            currentStudent.setInsurance(student.isInsurance());
        }
        if (student.isSpecialneeds() != currentStudent.isSpecialneeds())
        {
            currentStudent.setSpecialneeds(student.isSpecialneeds());
        }
        if (student.getRelationship() != null)
        {
            currentStudent.setRelationship(student.getRelationship());
        }
        if (student.getContactphone() != null)
        {
            currentStudent.setContactphone(student.getContactphone());
        }
        if (student.getContactemail() != null)
        {
            currentStudent.setContactemail(student.getContactemail());
        }
        if (student.getBackgroundinfo() != null)
        {
            currentStudent.setBackgroundinfo(student.getBackgroundinfo());
        }
        if (student.getCriticalinfo() != null)
        {
            currentStudent.setCriticalinfo(student.getCriticalinfo());
        }
        // TODO add visits to update
        return studentRepository.save(currentStudent);
    }

    @Transactional
    @Override
    public void delete(long id)
    {
        studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student ID " + id + " not found" ));
        studentRepository.deleteById(id);
    }
}
