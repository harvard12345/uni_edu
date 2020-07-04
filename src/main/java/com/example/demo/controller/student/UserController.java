package com.example.demo.controller.student;

import com.example.demo.entity.User;
import com.example.demo.service.MarkService;
import com.example.demo.service.PaymentService;
import com.example.demo.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("studentUserController")
@RequestMapping("/student")
public class UserController {

    private UserService userService;

    private PaymentService paymentService;

    private MarkService markService;

    public UserController(UserService userService, PaymentService paymentService, MarkService markService) {
        this.userService = userService;
        this.paymentService = paymentService;
        this.markService = markService;
    }

    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        model.addAttribute("statistics", this.paymentService.findStatistics(user));
        return "profile";
    }
}
