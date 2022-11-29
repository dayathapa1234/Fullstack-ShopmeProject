package com.shopme.admin.user;


import com.shopme.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmail(String email);

    void deleteByEmail(String email);
}
