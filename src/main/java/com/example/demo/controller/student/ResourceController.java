package com.example.demo.controller.student;

import com.example.demo.service.LinkService;
import com.example.demo.service.ResourceService;
import com.example.demo.service.TutorialService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/student")
@Controller("studentResourceController")
public class ResourceController {

    private ResourceService resourceService;

    private TutorialService tutorialService;

    private LinkService linkService;

    public ResourceController(ResourceService resourceService, TutorialService tutorialService, LinkService linkService) {
        this.resourceService = resourceService;
        this.tutorialService = tutorialService;
        this.linkService = linkService;
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download(@RequestParam Long id) {
        var resource = this.resourceService.findById(id);
        return ResponseEntity.ok().
                contentLength(resource.getSize()).
                contentType(MediaType.parseMediaType(resource.getContentType())).
                header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + resource.getOriginalName() + "\"").
                body(new ByteArrayResource(resource.getContent()));
    }

    @GetMapping("/resource")
    public String resource(@RequestParam Long tutorialId, Model model) {
        model.addAttribute("resources", this.resourceService.findAllByTutorial(tutorialId));
        model.addAttribute("links", this.linkService.findAllByTutorial(tutorialId));
        return "resource";
    }

    @GetMapping("/resourceBack")
    public String resourceBack(@RequestParam Long tutorialId) {
        return "redirect:/student/tutorial?courseId=" + this.tutorialService.findCourseId(tutorialId);
    }

}
