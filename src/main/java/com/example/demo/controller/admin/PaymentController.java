package com.example.demo.controller.admin;

import com.example.demo.entity.Transaction;
import com.example.demo.service.CourseService;
import com.example.demo.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class PaymentController {

    private PaymentService paymentService;

    private CourseService courseService;

    public PaymentController(PaymentService paymentService, CourseService courseService) {
        this.paymentService = paymentService;
        this.courseService = courseService;
    }

    @GetMapping("/payment")
    public String payment(Model model) {
        model.addAttribute("courses", this.courseService.findAll());
        model.addAttribute("payments", List.of());
        return "payment";
    }

    @GetMapping("/unpaidSearch")
    public String unpaidSearch(Model model, @RequestParam Long courseId) {
        model.addAttribute("payments", this.paymentService.findUnpaidByCourse(courseId));
        model.addAttribute("courses", this.courseService.findAll());
        return "payment";
    }

    @GetMapping("/paymentSearch")
    public String paymentSearch(Model model, @RequestParam String username) {
        model.addAttribute("payments", this.paymentService.searchPayment(username));
        model.addAttribute("courses", this.courseService.findAll());
        return "payment";
    }

    @GetMapping("/transactionCreate")
    public String paymentCreate(Model model, @RequestParam Long paymentId) {
        var transaction = new Transaction();
        transaction.setPaymentId(paymentId);
        model.addAttribute("transaction", transaction);
        return "transactionCreate";
    }

    @PostMapping("/transactionCreate")
    public String paymentCreate(@Valid Transaction transaction, Errors errors) {
        if (errors.hasErrors()) {
            return "transactionCreate";
        }
        this.paymentService.save(transaction);
        return "redirect:/admin/payment";
    }

    @GetMapping("/transactionDelete")
    public String paymentDelete(@RequestParam Long id) {
        this.paymentService.delete(id);
        return "redirect:/admin/payment";
    }


}
