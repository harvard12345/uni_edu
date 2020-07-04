package com.example.demo.controller.instructor;

import com.example.demo.entity.Link;
import com.example.demo.service.LinkService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("linkInstructorController")
@RequestMapping("/instructor")
public class LinkController {

    private LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/linkCreate")
    public String linkCreate(@RequestParam Long tutorialId, Model model) {
        var link = new Link();
        link.setTutorialId(tutorialId);
        model.addAttribute("link", link);
        return "linkCreate";
    }

    @GetMapping("/linkBack")
    public String linkBack(@RequestParam Long tutorialId) {
        return "redirect:/instructor/resource?tutorialId=" + tutorialId;
    }

    @PostMapping("/linkCreate")
    public String linkCreate(Link link) {
        this.linkService.save(link);
        return "redirect:/instructor/resource?tutorialId=" + link.getTutorialId();
    }

    @GetMapping("/linkDelete")
    public String linkDelte(@RequestParam Long id, @RequestParam Long tutorialId) {
        this.linkService.delete(id);
        return "redirect:/instructor/resource?tutorialId=" + tutorialId;
    }
}
