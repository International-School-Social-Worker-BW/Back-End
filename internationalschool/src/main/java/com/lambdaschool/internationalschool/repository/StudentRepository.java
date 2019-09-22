package com.lambdaschool.internationalschool.repository;

import com.lambdaschool.internationalschool.models.Student;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StudentRepository extends PagingAndSortingRepository<Student, Long>
{
}
