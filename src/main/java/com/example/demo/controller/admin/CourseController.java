package com.example.demo.controller.admin;

import com.example.demo.entity.Course;
import com.example.demo.service.CourseService;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller("adminCourseController")
@RequestMapping("/admin")
public class CourseController {

    private final CourseService courseService;

    private final UserService userService;

    public CourseController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @GetMapping("/course")
    public String course(Model model) {
        model.addAttribute("courses", this.courseService.findAll());
        return "course";
    }

    @GetMapping("/courseCreate")
    public String courseCreate(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("instructors", this.userService.findByAuthorityName("INSTRUCTOR"));
        return "courseCreate";
    }

    @GetMapping("/courseUpdate")
    public String courseEdit(Model model, @RequestParam Long id) {
        model.addAttribute("course", this.courseService.findById(id));
        model.addAttribute("instructors", this.userService.findByAuthorityName("INSTRUCTOR"));
        return "courseCreate";
    }

    @PostMapping("/courseCreate")
    public String courseEdit(@Valid Course course, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("instructors", this.userService.findByAuthorityName("INSTRUCTOR"));
            return "courseCreate";
        }
        this.courseService.save(course);
        return "redirect:/admin/course";
    }

    @GetMapping("/courseDelete")
    public String courseDelete(@RequestParam Long id) {
        this.courseService.delete(id);
        return "redirect:/admin/course";
    }

    @GetMapping("/courseByName")
    public String courseByName(@RequestParam String course, Model model) {
        model.addAttribute("courses", this.courseService.findAllByName(course));
        return "course";
    }
}
