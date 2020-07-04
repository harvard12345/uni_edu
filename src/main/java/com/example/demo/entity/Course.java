package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Min(value = 1, message = "Duration must be between 1 and 6!")
    @Max(value = 6, message = "Duration must be between 1 and 6!")
    private Integer duration;

    @Min(value = 0, message = "Fee must be greater than 0")
    private BigDecimal feePerMonth;

    @Min(value = 0, message = "Students must be greater than zero!")
    private Integer numberOfStudents;

    @Transient
    private Long instructorId;

    @ManyToMany(mappedBy = "courses")
    private List<User> users;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private User user;

}
