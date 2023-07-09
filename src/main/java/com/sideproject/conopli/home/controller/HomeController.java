package com.sideproject.conopli.home.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/docs")
@Slf4j
@RequiredArgsConstructor
public class HomeController {


    @GetMapping
    public ModelAndView home() {
        return new ModelAndView("docs/index.html");
    }
}
