package com.example.demo.controller.instructor;

import com.example.demo.entity.Resource;
import com.example.demo.service.LinkService;
import com.example.demo.service.ResourceService;
import com.example.demo.service.TutorialService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller("instructorResourceController")
@RequestMapping("/instructor")
public class ResourceController {

    private ResourceService resourceService;

    private TutorialService tutorialService;

    private LinkService linkService;

    public ResourceController(ResourceService resourceService, TutorialService tutorialService, LinkService linkService) {
        this.resourceService = resourceService;
        this.tutorialService = tutorialService;
        this.linkService = linkService;
    }

    @GetMapping("/resource")
    public String resource(@RequestParam Long tutorialId, Model model) {
        model.addAttribute("resources", this.resourceService.findAllByTutorial(tutorialId));
        model.addAttribute("links", this.linkService.findAllByTutorial(tutorialId));
        return "resource";
    }

    @GetMapping("/resourceCreate")
    public String resourceCrete(Model model, @RequestParam Long tutorialId) {
        var resource = new Resource();
        resource.setTutorialId(tutorialId);
        model.addAttribute("resource", resource);
        return "resourceCreate";
    }

    @PostMapping("/resourceCreate")
    public String resourceCreate(Resource resource) throws IOException {
        this.resourceService.save(resource);
        return "redirect:/instructor/resource?tutorialId=" + resource.getTutorialId();
    }

    @GetMapping("/resourceDelete")
    public String resourceDelete(@RequestParam Long id, @RequestParam Long tutorialId) {
        this.resourceService.delete(id);
        return "redirect:/instructor/resource?tutorialId=" + tutorialId;
    }

    @GetMapping("/resourceBack")
    public String resourceBack(@RequestParam Long tutorialId) {
        return "redirect:/instructor/tutorial?courseId=" + this.tutorialService.findCourseId(tutorialId);
    }


}
