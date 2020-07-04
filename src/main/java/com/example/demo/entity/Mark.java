package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@Entity
@NoArgsConstructor
public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;

    @Transient
    private Long studentId;

    @Min(value = 0, message = "Error: Mark must be greater than 0!")
    @Max(value = 100, message = "Error: Mark must be lower than 100!")
    private int mark;

    @Transient
    private Long courseId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User user;

    @ManyToOne
    private Course course;

    @PrePersist
    @PreUpdate
    public void date() {
        this.date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
    }

}
