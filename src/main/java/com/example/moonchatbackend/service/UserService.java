package com.example.moonchatbackend.service;

import com.example.moonchatbackend.model.User;
import com.example.moonchatbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
//    private PasswordEncoder passwordEncoder;

    public User registerUser(String username, String email, String password) {

        if (userRepository.existsByUsername(username)) {
            return null;
        }
        if (userRepository.existsByEmail(email)) {
            return null;
        }

//        String encryptedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        return userRepository.save(user);
    }


}

