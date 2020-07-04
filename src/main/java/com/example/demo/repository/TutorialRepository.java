package com.example.demo.repository;

import com.example.demo.entity.Course;
import com.example.demo.entity.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {

    List<Tutorial> findAllByCourse(Course course);
}
