package com.example.demo.controller.admin;

import com.example.demo.entity.Enrollment;
import com.example.demo.exception.StudentAlreadyEnrolledException;
import com.example.demo.service.CourseService;
import com.example.demo.service.EnrollmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/admin")
public class EnrollmentController {

    private final CourseService courseService;

    private final EnrollmentService enrollmentService;

    public EnrollmentController(CourseService courseService, EnrollmentService enrollmentService) {
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/enrollment")
    public String enrollment(Model model) {
        model.addAllAttributes(Map.of("courses", this.courseService.findAll(), "enrollment", new Enrollment()));
        return "enrollment";
    }

    @PostMapping("/enrollment")
    public String enrollment(Enrollment enrollment) throws StudentAlreadyEnrolledException {
        this.enrollmentService.save(enrollment);
        return "redirect:/";
    }
}
