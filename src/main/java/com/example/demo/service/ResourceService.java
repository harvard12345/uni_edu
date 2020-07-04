package com.example.demo.service;

import com.example.demo.entity.Resource;
import com.example.demo.entity.Tutorial;
import com.example.demo.repository.ResourceRepository;
import com.example.demo.repository.TutorialRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ResourceService {

    private final ResourceRepository resourceRepository;

    private final TutorialRepository tutorialRepository;

    public ResourceService(ResourceRepository resourceRepository, TutorialRepository tutorialRepository) {
        this.resourceRepository = resourceRepository;
        this.tutorialRepository = tutorialRepository;
    }

    @Transactional
    public void save(Resource resource) throws IOException {
        var multipart = resource.getMultipartFile();
        var tutorial = this.findTutorialById(resource.getTutorialId());
        resource.setContent(multipart.getBytes());
        resource.setContentType(multipart.getContentType());
        resource.setOriginalName(multipart.getOriginalFilename());
        resource.setSize(multipart.getSize());
        resource.setTutorial(tutorial);
        tutorial.setLastUpdate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")));
        this.tutorialRepository.save(tutorial);
        this.resourceRepository.save(resource);
    }

    public List<Resource> findAllByTutorial(Long tutorialId) {
        return this.resourceRepository.findAllByTutorial_Id(tutorialId);
    }

    public void delete(Long resourceId) {
        this.resourceRepository.findById(resourceId).ifPresent(this::delete);
    }

    public Resource findById(Long resourceId) {
        return this.resourceRepository.
                findById(resourceId).
                orElseThrow(() -> new NullPointerException("Resource with id " + resourceId + " does not exist!"));
    }

    private Tutorial findTutorialById(Long tutorialId) {
        return this.tutorialRepository.
                findById(tutorialId).
                orElseThrow(() -> new NullPointerException("Tutorial with id " + tutorialId + " does not exist!"));
    }

    @Transactional
    void delete(Resource resource) {
        var tutorial = resource.getTutorial();
        tutorial.setLastUpdate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")));
        this.tutorialRepository.save(tutorial);
        this.resourceRepository.delete(resource);
    }

}
