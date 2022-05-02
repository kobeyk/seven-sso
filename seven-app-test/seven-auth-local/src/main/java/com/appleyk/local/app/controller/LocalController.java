package com.appleyk.local.app.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/local")
public class LocalController {
    @GetMapping("/hello")
    public String hello(){
        return "hello local !";
    }
}
