package com.example.demo.service;

import com.example.demo.entity.Course;
import com.example.demo.entity.User;
import com.example.demo.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    private final UserService userService;

    public CourseService(CourseRepository courseRepository, UserService userService) {
        this.courseRepository = courseRepository;
        this.userService = userService;
    }

    public List<Course> findAll() {
        return this.courseRepository.findAll();
    }

    public Course findById(Long id) {
        return this.courseRepository.
                findById(id).
                orElseThrow(() -> new NullPointerException("Course with id " + id + " does not exist!"));
    }

    public void delete(Long id) {
        this.courseRepository.deleteById(id);
    }

    Optional<Course> findByName(String name) {
        return this.courseRepository.findByName(name);
    }

    public void save(Course course) {
        course.setUser(this.userService.findById(course.getInstructorId()));
        this.courseRepository.save(course);
    }

    public List<Course> findByInstructor(User user) {
        return this.courseRepository.findAllByUser(user);
    }

    public List<Course> findAllByName(String courseName) {
        return this.courseRepository.findAllByNameLike(courseName + "%");
    }

    @Transactional
    public List<Course> findByStudent(User user) {
        var courses = this.userService.findById(user.getId()).getCourses();
        courses.size();
        return courses;
    }

    @Transactional
    public List<User> findByCourse(Long id) {
        var students = this.findById(id).getUsers();
        var size = students.size();
        return students;
    }


}
