package com.appleyk.sso.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>越努力，越幸运</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on  下午4:57 2022/3/27
 */
@CrossOrigin
@RestController
@RequestMapping("/hello")
public class HelloController {
    @GetMapping("/t1")
    public String hello(){
        return "hello";
    }
}
