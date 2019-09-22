package com.lambdaschool.internationalschool.repository;

import com.lambdaschool.internationalschool.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>
{
    User findByUseremail(String username);
}
