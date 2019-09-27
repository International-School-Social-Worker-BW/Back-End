package com.lambdaschool.internationalschool.controllers;

import com.lambdaschool.internationalschool.models.ErrorDetail;
import com.lambdaschool.internationalschool.models.Visit;
import com.lambdaschool.internationalschool.services.VisitService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/visits")
public class VisitsController
{
    @Autowired
    VisitService visitService;

    // POST localhost:2019/visits/student/{studentid}/user/{userid}
    @ApiOperation(value = "Add a new Visit", notes = "The Student Id will be used to create a link to the user id", response = void.class)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Visit Created", response = void.class),
            @ApiResponse(code = 500, message = "Visit Creation Failed", response = ErrorDetail.class)})
    @PostMapping(value = "/student/{studentid}/user/{userid}", consumes = {"application/json"}, produces = "application/json")
    public ResponseEntity<?> addNewVisit(@Valid @RequestBody
                                                 Visit visit, @PathVariable long studentid, @PathVariable long userid) throws URISyntaxException
    {
        visitService.save(visit, studentid, userid);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }
}
