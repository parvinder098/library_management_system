package com.example.library.service.Impl;

import com.example.library.model.IssuedBook;
import com.example.library.model.User;
import com.example.library.repository.IssuedBookRepository;
import com.example.library.repository.UserRepository;
import com.example.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final IssuedBookRepository issuedBookRepository;
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }



    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
        );
    }
    public void markAsReturned(Long issuedBookId) {
        Optional<IssuedBook> optionalIssuedBook = issuedBookRepository.findById(issuedBookId);
        if (optionalIssuedBook.isPresent()) {
            IssuedBook issuedBook = optionalIssuedBook.get();
            if (issuedBook.getReturnDate() == null) {
                issuedBook.setReturnDate(LocalDate.now());
                issuedBookRepository.save(issuedBook);
            }
        }
    }
    @Override
    public List<User> getAllStudents() {
        return userRepository.findByRole("ROLE_STUDENT");  // ✅ CORRECTED
    }

    @Override
    public User saveUser(User user) {
        // ✅ Automatically set name = username if name is null or empty
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            user.setName(user.getUsername());
        }
        return userRepository.save(user);
    }


}
