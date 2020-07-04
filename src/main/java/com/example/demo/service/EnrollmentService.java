package com.example.demo.service;

import com.example.demo.entity.Enrollment;
import com.example.demo.entity.Payment;
import com.example.demo.exception.StudentAlreadyEnrolledException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

@Service
public class EnrollmentService {

    private final JdbcTemplate jdbcTemplate;

    private final UserService userService;

    public EnrollmentService(JdbcTemplate jdbcTemplate, UserService userService) {
        this.jdbcTemplate = jdbcTemplate;
        this.userService = userService;
    }

    public void save(Enrollment enrollment) throws StudentAlreadyEnrolledException {
        var userId = this.userService.findByUsername(enrollment.getUsername()).getId();
        var courseId = enrollment.getCourseId();
        if (enrollment.getType() == 1) {
            if (isEnrolled(userId, courseId)) {
                throw new StudentAlreadyEnrolledException("Student with username " + enrollment.getUsername() +
                        " is already enrolled for the course!");
            }
            this.jdbcTemplate.update("insert into uniedu.user_courses(users_id, courses_id, date) VALUE (?,?,?)", userId, courseId, LocalDate.now());
        } else {
            this.jdbcTemplate.update("delete from uniedu.user_courses where users_id = ? and courses_id = ?", userId, courseId);
        }
    }

    private boolean isEnrolled(Long userId, Long courseId) {
        var query = "select * from uniedu.user_courses where users_id = ? and courses_id = ?";
        RowMapper<Date> mapperFunction = (rs, numRow) -> rs.getDate("date");
        try {
            this.jdbcTemplate.queryForObject(query, mapperFunction, userId, courseId);
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }
        return true;
    }

    LocalDate findOne(Payment payment) {
        var query = "select * from uniedu.user_courses where users_id = ? and courses_id = ?";
        RowMapper<LocalDate> mapperFunction = (rs, numRows) -> rs.getDate("date").toLocalDate();
        return Objects.requireNonNull(this.jdbcTemplate.
                queryForObject(query, mapperFunction, payment.getUser().getId(), payment.getCourse().getId()));
    }
}