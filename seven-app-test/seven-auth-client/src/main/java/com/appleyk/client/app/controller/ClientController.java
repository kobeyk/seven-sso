package com.appleyk.client.app.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/client")
public class ClientController {
    @GetMapping("/hello")
    public String hello(){
        return "hello client !";
    }
}
