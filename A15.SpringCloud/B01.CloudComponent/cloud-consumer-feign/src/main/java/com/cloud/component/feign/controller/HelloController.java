package com.cloud.component.feign.controller;

import com.cloud.component.feign.service.SchedualHiService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    SchedualHiService schedualHiService;

    @GetMapping
    public String sayHi(@RequestParam String name) {
        return schedualHiService.sayHiFromClientOne(name);
    }
}
