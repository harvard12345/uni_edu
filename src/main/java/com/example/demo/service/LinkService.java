package com.example.demo.service;

import com.example.demo.entity.Link;
import com.example.demo.entity.Tutorial;
import com.example.demo.repository.LinkRepository;
import com.example.demo.repository.TutorialRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class LinkService {

    private final LinkRepository linkRepository;

    private final TutorialRepository tutorialRepository;


    public LinkService(LinkRepository linkRepository, TutorialRepository tutorialRepository) {
        this.linkRepository = linkRepository;
        this.tutorialRepository = tutorialRepository;
    }

    public List<Link> findAllByTutorial(Long tutorialId) {
        return this.linkRepository.findAllByTutorial(this.findTutorialById(tutorialId));
    }

    @Transactional
    public void save(Link link) {
        var tutorial = this.findTutorialById(link.getTutorialId());
        link.setTutorial(tutorial);
        tutorial.setLastUpdate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")));
        this.tutorialRepository.save(tutorial);
        this.linkRepository.save(link);
    }

    public void delete(Long linkId) {
        this.linkRepository.findById(linkId).
                ifPresent(this::delete);
    }

    private Tutorial findTutorialById(Long tutorialId) {
        return this.tutorialRepository.
                findById(tutorialId).
                orElseThrow(() -> new NullPointerException("Tutorial with id " + tutorialId + " does not exist!"));
    }

    @Transactional
    void delete(Link link) {
        var tutorial = link.getTutorial();
        tutorial.setLastUpdate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")));
        this.tutorialRepository.save(tutorial);
        this.linkRepository.delete(link);
    }
}
