package com.example.library.controller;

import com.example.library.model.User;
import com.example.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    // ✅ Show login form
    @GetMapping("/login")
    public String login() {
        return "login"; // renders login.html
    }

    // ✅ Show registration form
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // renders register.html
    }

    // ✅ Handle user registration
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        String password = user.getPassword();

        // ✅ Validate password
        if (!password.matches("^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-={}\\[\\]:\";'<>?,./]).{8,}$")) {
            redirectAttributes.addFlashAttribute("error", "Password must be at least 8 characters long, include one uppercase letter, and one special character.");
            return "redirect:/register";
        }

        // ✅ Encode and save
        user.setPassword(passwordEncoder.encode(password));
        userService.saveUser(user);

        redirectAttributes.addFlashAttribute("success", "Registration successful. Please login.");
        return "redirect:/login";
    }


    // ✅ Redirect after login based on role
    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return "redirect:/admin/dashboard";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_STUDENT"))) {
            return "redirect:/student/dashboard";
        } else {
            return "redirect:/login?error=true";
        }
    }
}
