package com.example.demo.service;

import com.example.demo.entity.Tutorial;
import com.example.demo.repository.TutorialRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class TutorialService {

    private final TutorialRepository tutorialRepository;

    private final CourseService courseService;

    public TutorialService(TutorialRepository tutorialRepository, CourseService courseService) {
        this.tutorialRepository = tutorialRepository;
        this.courseService = courseService;
    }

    public Tutorial findById(Long id) {
        return this.tutorialRepository.
                findById(id).
                orElseThrow(() -> new NullPointerException("Tutorial with id " + id + " does not exist!"));
    }

    public Long findCourseId(Long id) {
        return this.findById(id).getCourse().getId();
    }

    public void save(Tutorial tutorial) {
        tutorial.setCourse(this.courseService.findById(tutorial.getCourseId()));
        this.tutorialRepository.save(tutorial);
    }

    public void delete(Long id) {
        this.tutorialRepository.deleteById(id);
    }

    public List<Tutorial> findAllByCourseId(Long id) {
        var course = this.courseService.findById(id);
        var tutorials = this.tutorialRepository.findAllByCourse(course);
        var matcher = Pattern.compile("(.+?)[-._]([0-9]{1,2})").matcher(course.getName());
        if (matcher.matches()) {
            var suffix = matcher.group(1).trim();
            var prefix = Integer.parseInt(matcher.group(2));
            var optionalCourse = this.courseService.findByName(suffix);
            if (optionalCourse.isPresent()) {
                var masterCourses = this.tutorialRepository.findAllByCourse(optionalCourse.get()).
                        stream().
                        filter(x -> x.getLevel() >= prefix).
                        collect(Collectors.toList());
                masterCourses.addAll(tutorials);
                return masterCourses;
            }
        }

        return tutorials;
    }
}
