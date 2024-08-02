package com.example.Volunteer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String registerUser(@RequestParam String email, @RequestParam String password, Model model) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        if (userService.isEmailAlreadyRegistered(email)) {
            model.addAttribute("registerError", "Email already exists");
            return "register";
        }

        userService.registerUser(user);
        return "redirect:/";
    }

    @PostMapping("/login")  // Added @PostMapping annotation
    public String loginUser(@RequestParam String email, @RequestParam String password, 
                            Model model, HttpSession session) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        String loginResponse = userService.loginUser(user);
        if ("Login successful!".equals(loginResponse)) {
            // Store user in session
            session.setAttribute("loggedInUser", user);
            return "redirect:/profile";
        }

        model.addAttribute("loginError", loginResponse);
        return "index";
    }

    @GetMapping("/profile")
    public String showProfile(Model model, HttpSession session) {
        // Retrieve the logged-in user from the session
        User currentUser = (User) session.getAttribute("loggedInUser");

        if (currentUser == null) {
            // If no user is logged in, redirect to login page
            return "redirect:/";
        }

        model.addAttribute("user", currentUser);
        return "profile";
    }
}
