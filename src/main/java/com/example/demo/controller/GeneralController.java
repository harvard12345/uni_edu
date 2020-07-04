package com.example.demo.controller;

import com.example.demo.exception.StudentAlreadyEnrolledException;
import com.example.demo.exception.StudentNotEnrolledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralController {

    @ExceptionHandler(value = StudentNotEnrolledException.class)
    public String studentNotEnrolledException(StudentNotEnrolledException ex, Model model) {
        model.addAttribute("exceptionMessage", ex.getMessage());
        return "exception";
    }


    @ExceptionHandler(value = StudentAlreadyEnrolledException.class)
    public String studentAlreadyEnrolledException(StudentAlreadyEnrolledException ex, Model model) {
        model.addAttribute("exceptionMessage", ex.getMessage());
        return "exception";
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public String usernameNotFoundException(UsernameNotFoundException ex, Model model) {
        model.addAttribute("exceptionMessage", ex.getMessage());
        return "exception";
    }


}
