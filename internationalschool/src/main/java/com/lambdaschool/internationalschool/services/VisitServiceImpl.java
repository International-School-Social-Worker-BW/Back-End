package com.lambdaschool.internationalschool.services;

import com.lambdaschool.internationalschool.exceptions.ResourceNotFoundException;
import com.lambdaschool.internationalschool.models.Student;
import com.lambdaschool.internationalschool.models.User;
import com.lambdaschool.internationalschool.models.Visit;
import com.lambdaschool.internationalschool.repository.StudentRepository;
import com.lambdaschool.internationalschool.repository.UserRepository;
import com.lambdaschool.internationalschool.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service(value = "visitService")
public class VisitServiceImpl implements VisitService
{
    @Autowired
    VisitRepository visitRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<Visit> findAll()
    {
        List<Visit> list = new ArrayList<>();
        visitRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Transactional
    @Override
    public Visit save(Visit visit, long studentid, long userid)
    {
        Student currentStudent = studentRepository.findById(studentid).orElseThrow(() -> new ResourceNotFoundException("Student ID " + studentid + " not found" ));
        User currentUser = userRepository.findById(userid).orElseThrow(() -> new ResourceNotFoundException("User ID " + studentid + " not found" ));
        Visit newvisit = new Visit(new Date(), currentStudent, currentUser);

        return visitRepository.save(newvisit);
    }

    @Transactional
    @Override
    public void delete(long id)
    {
        visitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Visit ID " + id + " not found" ));
        visitRepository.deleteById(id);
    }

}
