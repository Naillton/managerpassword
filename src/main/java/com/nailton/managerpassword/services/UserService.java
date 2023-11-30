package com.nailton.managerpassword.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.nailton.managerpassword.models.User;
import com.nailton.managerpassword.repositories.UserRepository;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Value("${api.security.token.secret}")
    private String secret;

    public List<User> getUsers() {
        return (List<User>) this.userRepository.findAll();
    }

    public User getUserById(UUID id) {
        return this.userRepository.findById(id).orElse(null);
    }

    public void insertUser(User user) {
        String hashedPass = encodePass(user.getPassword());
        user.setPassword(hashedPass);
        this.userRepository.save(user);
    }

    public void updateUser(User user, UUID id) {
        User updateU = this.getUserById(id);
        String hashedPass = encodePass(user.getPassword());
        user.setPassword(hashedPass);
        updateU.setName(user.getName());
        updateU.setEmail(user.getEmail());
        updateU.setPassword(user.getPassword());
        this.userRepository.save(updateU);
    }

    public User findUserByEmail(String email) {
        return this.userRepository.findUserByEmail(email);
    }

    public String findUserByEmailAndPassword(String email, String password) {
        User user = this.findUserByEmail(email);
        if (user != null) {
            boolean isTrue = this.decodePass(password, user.getPassword());
            if (isTrue) {
                return generateToken(user);
            } else {
                return "User Not Found";
            }
        } else {
            return "User Not Found";
        }
    }

    private String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer("API_MP")
                .withSubject(user.getEmail())
                .withExpiresAt(expirationDate())
                .sign(algorithm);
    }

    private Instant expirationDate() {
        return LocalDateTime.now()
                .plusHours(4)
                .toInstant(ZoneOffset.of("-04:00"));
    }

    public String validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm)
                .withIssuer("API_MP")
                .build()
                .verify(token)
                .getSubject();
    }

    public String encodePass(String pass) {
        return new BCryptPasswordEncoder().encode(pass);
    }

    private boolean decodePass(String rawPass, String encondedPass) {
        return new BCryptPasswordEncoder().matches(rawPass, encondedPass);
    }
}
