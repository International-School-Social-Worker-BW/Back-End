package com.lambdaschool.internationalschool.controllers;

import com.lambdaschool.internationalschool.models.Visit;
import com.lambdaschool.internationalschool.services.VisitService;
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
    @PostMapping(value = "/student/{studentid}/user/{userid}", consumes = {"application/json"}, produces = "application/json")
    public ResponseEntity<?> addNewVisit(@Valid @RequestBody
                                                 Visit visit, @PathVariable long studentid, @PathVariable long userid) throws URISyntaxException
    {
        visitService.save(visit, studentid, userid);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }
}
