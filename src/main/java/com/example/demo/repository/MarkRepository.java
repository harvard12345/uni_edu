package com.example.demo.repository;

import com.example.demo.entity.Mark;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarkRepository extends JpaRepository<Mark, Long> {


    List<Mark> findAllByCourse_Id(Long courseId);

    List<Mark> findAllByUser(User user);

}
