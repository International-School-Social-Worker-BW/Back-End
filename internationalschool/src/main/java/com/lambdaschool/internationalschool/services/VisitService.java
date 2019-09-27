package com.lambdaschool.internationalschool.services;

import com.lambdaschool.internationalschool.models.Visit;

import java.util.List;

public interface VisitService
{
    List<Visit> findAll();

    Visit save(Visit visit, long studentid, long userid);

    void delete(long id);

    Visit update(Visit visit);
}
