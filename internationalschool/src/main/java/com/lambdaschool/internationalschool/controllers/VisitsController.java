package com.lambdaschool.internationalschool.controllers;

import com.lambdaschool.internationalschool.services.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/visits")
public class VisitsController
{
    @Autowired
    VisitService visitService;

//    @PostMapping
//    public ResponseEntity<?> addNewVisit()
}
