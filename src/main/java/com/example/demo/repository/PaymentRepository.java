package com.example.demo.repository;

import com.example.demo.entity.Course;
import com.example.demo.entity.Payment;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByCourse_Id(Long id);

    Optional<Payment> findByUserAndCourse(User user, Course course);

    List<Payment> findAllByUserAndCourse(User user, Course course);

    List<Payment> findAllByUser(User user);

}
