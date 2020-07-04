package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@NoArgsConstructor(force = true)
public class Ranking {

    @Id
    private Long ranking;

    private String student;

    private Integer totalMark;

    private Long course_id;

    public Ranking(Long ranking, String student, Integer totalMark) {
        this.ranking = ranking;
        this.student = student;
        this.totalMark = totalMark;
    }

    public Ranking(Long ranking, String student, Integer totalMark, Long course_id) {
        this.ranking = ranking;
        this.student = student;
        this.totalMark = totalMark;
        this.course_id = course_id;
    }
}
