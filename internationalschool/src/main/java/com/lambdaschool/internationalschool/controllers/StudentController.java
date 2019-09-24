package com.lambdaschool.internationalschool.controllers;

import com.lambdaschool.internationalschool.models.ErrorDetail;
import com.lambdaschool.internationalschool.models.Student;
import com.lambdaschool.internationalschool.services.StudentService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integr", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})

    // GET localhost:2019/students/students
    @ApiOperation(value = "Get All Students", response = Student.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Students Found", response = Student.class),
            @ApiResponse(code = 404, message = "Students Not Found", response = ErrorDetail.class)})
    @GetMapping(value = "/students", produces = {"application/json"})
    public ResponseEntity<?> listAllStudents()
    {
        List<Student> myStudents = studentService.findAll();
        return new ResponseEntity<>(myStudents, HttpStatus.OK);
    }
    // GET localhost:2019/students/student/1
    @ApiOperation(value = "Get a Student by ID", response = Student.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Student Found", response = Student.class),
            @ApiResponse(code = 404, message = "Student Not Found", response = ErrorDetail.class)})
    @GetMapping(value = "/student/{studentid}", produces = {"application/json"})
    public ResponseEntity<?> findStudentById(@PathVariable long studentid)
    {
        Student s = studentService.findStudentById(studentid);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    // POST localhost:2019/students/new
    @ApiOperation(value = "Create a new Student", notes = "The newly created Student id will be sent in the location header", response = void.class)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Student Created", response = void.class),
            @ApiResponse(code = 500, message = "Student Creation Failed", response = ErrorDetail.class)})
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/new", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addNewStudent(@Valid @RequestBody Student newStudent) throws URISyntaxException
    {
        newStudent = studentService.save(newStudent);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newStudentURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{studentid}")
                .buildAndExpand(newStudent.getStudentid())
                .toUri();
        responseHeaders.setLocation(newStudentURI);
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    // PUT localhost:2019/students/student/1
    @ApiOperation(value = "Edit a Student based on Id", response = void.class)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Student Edited", response = void.class),
            @ApiResponse(code = 500, message = "Student Editing Failed", response = ErrorDetail.class)})
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PutMapping(value = "/student/{studentid}")
    public ResponseEntity<?> updateStudent(@RequestBody Student updateStudent, @PathVariable long studentid)
    {
        studentService.update(updateStudent, studentid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // DELETE localhost:2019/students/student/1
    @ApiOperation(value = "Delete a Student based on Id", response = void.class)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Student Deleted", response = void.class),
            @ApiResponse(code = 500, message = "Student Delete Failed", response = ErrorDetail.class)})
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @DeleteMapping(value = "/student/{studentid}")
    public ResponseEntity<?> deleteStudentById(@PathVariable long studentid)
    {
        studentService.delete(studentid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
