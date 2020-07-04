package com.example.demo.controller.instructor;

import com.example.demo.entity.Tutorial;
import com.example.demo.entity.User;
import com.example.demo.service.TutorialService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller("instructorTutorialController")
@RequestMapping("/instructor")
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

    @GetMapping("/tutorialCreate")
    public String tutorialCreate(Model model, @RequestParam Long courseId) {
        var tutorial = new Tutorial();
        tutorial.setCourseId(courseId);
        model.addAttribute("tutorial", tutorial);
        return "tutorialCreate";
    }

    @PostMapping("/tutorialCreate")
    public String tutorialCreate(@Valid Tutorial tutorial, Errors errors, @AuthenticationPrincipal User user) {
        if (errors.hasErrors()){
            return "tutorialCreate";
        }
        tutorial.setUser(user);
        this.tutorialService.save(tutorial);
        return "redirect:/instructor/tutorial?courseId=" + tutorial.getCourseId();
    }

    @GetMapping("/tutorialDelete")
    public String tutorialDelete(@RequestParam Long id, @RequestParam Long courseId) {
        this.tutorialService.delete(id);
        return "redirect:/instructor/tutorial?courseId=" + courseId;
    }

    @GetMapping("/tutorialUpdate")
    public String tutorialUpdate(@RequestParam Long id, @RequestParam Long courseId, Model model) {
        var tutorial = this.tutorialService.findById(id);
        tutorial.setCourseId(courseId);
        model.addAttribute("tutorial", tutorial);
        return "tutorialCreate";
    }


}
