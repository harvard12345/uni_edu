package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @Transient
    private Long tutorialId;

    @JoinColumn(name = "tutorial_id")
    @ManyToOne
    private Tutorial tutorial;
}
