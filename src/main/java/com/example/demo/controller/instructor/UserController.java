package com.example.demo.controller.instructor;

import com.example.demo.entity.Mark;
import com.example.demo.service.CourseService;
import com.example.demo.service.MarkService;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller("userInstructorController")
@RequestMapping("/instructor")
public class UserController {


    private CourseService courseService;

    private MarkService markService;

    public UserController(CourseService courseService, MarkService markService) {
        this.courseService = courseService;
        this.markService = markService;
    }

    @GetMapping("/mark")
    public String mark(Model model, @RequestParam Long studentId, @RequestParam Long courseId) {
        var mark = new Mark();
        mark.setStudentId(studentId);
        mark.setCourseId(courseId);
        model.addAttribute("mark", mark);
        return "marking";
    }

    @PostMapping("/mark")
    public String mark(@Valid Mark mark, Errors errors) {
        if (errors.hasErrors()) {
            return "marking";
        }
        this.markService.save(mark);
        return "redirect:/instructor/studentByCourse?courseId=" + mark.getCourseId();
    }

    @GetMapping("/studentByCourse")
    public String studentByCourse(@RequestParam Long courseId, Model model) {
        model.addAttribute("students", this.courseService.findByCourse(courseId));
        return "students";
    }

    @GetMapping("/markBack")
    public String markBack(@RequestParam Long courseId) {
        return "redirect:/instructor/studentByCourse?courseId=" + courseId;
    }
}
