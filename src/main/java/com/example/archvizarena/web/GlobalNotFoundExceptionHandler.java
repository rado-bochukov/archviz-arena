package com.example.archvizarena.web;

import com.example.archvizarena.service.exception.ObjectNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalNotFoundExceptionHandler {
    @ExceptionHandler({ObjectNotFoundException.class, IllegalArgumentException.class})
    public ModelAndView handleUserNotFoundException(RuntimeException exception){
        ModelAndView modelAndView=new ModelAndView("not-found-exception");
        modelAndView.addObject("message",exception.getMessage());
        return modelAndView;
    }
}
