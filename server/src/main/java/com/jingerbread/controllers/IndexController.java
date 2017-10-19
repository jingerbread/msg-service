package com.jingerbread.controllers;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController implements ErrorController {//todo

    @RequestMapping(value = "/error")
    public String error() {
        return "Message Service";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}