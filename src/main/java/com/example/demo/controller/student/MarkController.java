package com.example.demo.controller.student;

import com.example.demo.service.MarkService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/student")
public class MarkController {

    private MarkService markService;

    public MarkController(MarkService markService) {
        this.markService = markService;
    }

    @GetMapping("/mark")
    public String mark(Model model, @RequestParam Long courseId){
        model.addAttribute("marks", this.markService.findAllByCourseId(courseId));
        model.addAttribute("summaryStatistics", this.markService.findMarkStatistics(courseId));
        return "marks";
    }
}
