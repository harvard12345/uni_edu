package com.example.demo.controller.admin;

import com.example.demo.entity.Discount;
import com.example.demo.service.DiscountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class DiscountController {

    private DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping("/discount")
    public String discount(Model model) {
        model.addAttribute("discounts", this.discountService.findAll());
        return "discounts";
    }

    @GetMapping("/discountCreate")
    public String discountCreate(Model model) {
        model.addAttribute("discount", new Discount());
        return "discountCreate";
    }

    @PostMapping("/discountCreate")
    public String discountCreate(Discount discount) {
        this.discountService.save(discount);
        return "redirect:/admin/discount";
    }

    @GetMapping("/discountUpdate")
    public String discountUpdate(@RequestParam Long discountId, Model model) {
        model.addAttribute("discount", this.discountService.findById(discountId));
        return "discountCreate";
    }

    @GetMapping("/discountDelete")
    public String discountDelete(@RequestParam Long discountId) {
        this.discountService.delete(discountId);
        return "redirect:/admin/discount";
    }
}
