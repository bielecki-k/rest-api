package com.bielecki.restapi.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorlRestController {

    @GetMapping("/api/hello")
    public Greeting hello(){
        Greeting greeting = new Greeting();
        greeting.setMsg("hello");
        return greeting;
    }
}
