package com.example.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public boolean isEmailAlreadyRegistered(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public String loginUser(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            return "Login successful!";
        }
        return "Invalid credentials!";
    }
}