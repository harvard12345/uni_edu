package com.example.demo.repository;

import com.example.demo.entity.Link;
import com.example.demo.entity.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkRepository extends JpaRepository<Link, Long> {
    List<Link> findAllByTutorial(Tutorial tutorial);
}
