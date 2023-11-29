package com.nailton.managerpassword.services;

import com.nailton.managerpassword.repositories.PasswordsRepository;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class PasswordService {

    @Autowired
    private PasswordsRepository passwordsRepository;
}
