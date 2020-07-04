package com.example.demo.controller.admin;

import com.example.demo.entity.User;
import com.example.demo.service.CourseService;
import com.example.demo.service.PaymentService;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller("adminUserController")
@RequestMapping("/admin")
public class UserController {

    private final UserService userService;

    private final CourseService courseService;

    private final PaymentService paymentService;

    public UserController(UserService userService, CourseService courseService, PaymentService paymentService) {
        this.userService = userService;
        this.courseService = courseService;
        this.paymentService = paymentService;
    }

    @GetMapping("/student")
    public String student(Model model) {
        model.addAttribute("students", this.userService.findByAuthorityName("STUDENT"));
        return "students";
    }

    @GetMapping("/studentByCourse")
    public String studentByCourse(@RequestParam Long courseId, Model model) {
        model.addAttribute("students", this.courseService.findByCourse(courseId));
        return "students";
    }

    @GetMapping("/studentProfile")
    public String profile(Model model, @RequestParam Long id, @RequestParam Long courseId) {
        var user = this.userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("courseId", courseId);
        model.addAttribute("statistics", this.paymentService.findStatistics(user));
        return "profile";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            return "register";
        }
        this.userService.save(user);
        return "redirect:/";
    }

    @GetMapping("/deleteUser")
    public String delete(@RequestParam Long id) {
        this.userService.delete(id);
        return "redirect:/admin/student";
    }

    @GetMapping("/userByUsername")
    public String studentByUsername(Model model, @RequestParam String username) {
        model.addAttribute("users", this.userService.findAllByUsernameLike(username));
        return "users";
    }

    @GetMapping("/user")
    public String user(Model model) {
        model.addAttribute("users", this.userService.findAll());
        return "users";
    }
}
