package com.nailton.managerpassword.repositories;

import com.nailton.managerpassword.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

    @Query(value = "SELECT * FROM user WHERE email=?", nativeQuery = true)
    User findUserByEmail(String email);

    @Query(value = "SELECT * FROM user WHERE email=? AND password=?", nativeQuery = true)
    User findUserByEmailAndPassword(String email, String password);
}
