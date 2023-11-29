package com.nailton.managerpassword.repositories;

import com.nailton.managerpassword.models.Passwords;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PasswordsRepository extends CrudRepository<Passwords, UUID> {
}
