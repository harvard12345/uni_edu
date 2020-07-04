package com.example.demo.controller.student;

import com.example.demo.service.TutorialService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("studentTutorialController")
@RequestMapping("/student")
public class TutorialController {

    private TutorialService tutorialService;

    public TutorialController(TutorialService tutorialService) {
        this.tutorialService = tutorialService;
    }

    @GetMapping("/tutorial")
    public String tutorial(Model model, @RequestParam Long courseId) {
        model.addAttribute("tutorials", this.tutorialService.findAllByCourseId(courseId));
        return "tutorial";
    }
}
