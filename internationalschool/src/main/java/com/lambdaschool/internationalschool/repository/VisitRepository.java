package com.lambdaschool.internationalschool.repository;

import com.lambdaschool.internationalschool.models.Visit;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface VisitRepository extends PagingAndSortingRepository<Visit, Long>
{
}
