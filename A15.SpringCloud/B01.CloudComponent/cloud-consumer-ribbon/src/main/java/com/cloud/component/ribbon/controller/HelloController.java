package com.cloud.component.ribbon.controller;


import com.cloud.component.ribbon.service.HelloService;
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
    HelloService helloService;

    @GetMapping
    public String hi(@RequestParam String name)
    {
        return helloService.hiService(name);
    }
}
