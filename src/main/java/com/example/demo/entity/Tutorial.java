package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@Entity
public class Tutorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String lastUpdate;

    @Min(value = 1, message = "Error: Level must be greater than 0!")
    private Integer level;

    @Transient
    private Long courseId;

    @JoinColumn(name = "course_id")
    @ManyToOne
    private Course course;

    @JoinColumn(name = "instructor_id")
    @ManyToOne
    private User user;

    @PrePersist
    @PreUpdate
    public void setLastUpdate(){
        this.lastUpdate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm"));
    }

}
