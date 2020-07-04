package com.example.demo.service;

import com.example.demo.entity.Authority;
import com.example.demo.repository.AuthorityRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    Authority findByName(String name) {
        return this.authorityRepository.
                findByName(name).
                orElseThrow(() -> new NullPointerException("Authority with name " + name + " does not exist!"));
    }
}
