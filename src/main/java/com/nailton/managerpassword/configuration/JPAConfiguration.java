package com.nailton.managerpassword.configuration;

import com.nailton.managerpassword.services.PasswordService;
import com.nailton.managerpassword.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.nailton.managerpassword.repositories")
public class JPAConfiguration {

    @Bean("userService")
    public UserService userService() {
        return new UserService();
    }

    @Bean("paswordService")
    public PasswordService passwordService() {
        return new PasswordService();
    }
}
