package org.trinogin;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Indexed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/menu")
public class HelloController {

    @GetMapping("/list")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/view")
    public String pindex() {
        return "Greetings from Winter Boots!";
    }
}