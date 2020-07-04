package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final AuthorityService authorityService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(AuthorityService authorityService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authorityService = authorityService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public User findByUsername(String username) {
        return this.userRepository.
                findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException("Student with username " + username + " does not exist!"));
    }

    public List<User> findByAuthorityName(String name) {
        return this.userRepository.findAllByAuthority(this.authorityService.findByName(name));
    }

    public User findById(Long id) {
        return this.userRepository.
                findById(id).
                orElseThrow(() -> new NullPointerException("Student with id " + id + " does not exist!"));
    }

    public User save(User user) {
        user.setAuthority(this.authorityService.findByName(user.getRole()));
        user.setPassword(this.passwordEncoder.encode(user.getRawPassword()));
        return this.userRepository.save(user);
    }

    public void delete(Long id) {
        this.userRepository.deleteById(id);
    }

    public List<User> findAllByUsernameLike(String token) {
        return this.userRepository.
                findAllByUsernameLike(token + "%");
    }

}


