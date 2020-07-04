package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contentType;

    private long size;

    private byte[] content;

    private String originalName;

    @Transient
    private Long tutorialId;

    @Transient
    private MultipartFile multipartFile;

    @ManyToOne
    @JoinColumn(name = "tutorial_id")
    private Tutorial tutorial;

}
