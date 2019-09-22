package com.lambdaschool.internationalschool.controllers;

import com.lambdaschool.internationalschool.models.Student;
import com.lambdaschool.internationalschool.services.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController
{
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    StudentService studentService;

    // GET localhost:2019/students/students
    @GetMapping(value = "/students", produces = {"application/json"})
    public ResponseEntity<?> listAllStudents()
    {
        List<Student> myStudents = studentService.findAll();
        return new ResponseEntity<>(myStudents, HttpStatus.OK);
    }
    // GET localhost:2019/students/student/1
    @GetMapping(value = "/student/{studentid}", produces = {"application/json"})
    public ResponseEntity<?> findStudentById(@PathVariable long studentid)
    {
        Student s = studentService.findStudentById(studentid);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    // POST localhost:2019/students/new
    @PostMapping(value = "/new", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addNewStudent(@Valid @RequestBody Student newStudent) throws URISyntaxException
    {
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newStudentURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{studentid}")
                .buildAndExpand(newStudent.getStudentid())
                .toUri();
        responseHeaders.setLocation(newStudentURI);
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    // PUT localhost:2019/students/student/1
    @PutMapping(value = "/student/{studentid")
    public ResponseEntity<?> updateStudent(@RequestBody Student updateStudent, @PathVariable long studentid)
    {
        studentService.update(updateStudent, studentid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // DELETE localhost:2019/students/student/1
    @DeleteMapping(value = "/student/{studentid")
    public ResponseEntity<?> deleteStudentById(@PathVariable long studentid)
    {
        studentService.delete(studentid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
