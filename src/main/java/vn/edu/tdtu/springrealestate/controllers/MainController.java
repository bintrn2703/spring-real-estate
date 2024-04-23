package vn.edu.tdtu.springrealestate.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class MainController {
    @GetMapping("/")
    public String index() {

        return "index";
    }

    @GetMapping("/login")
    public String login() {

        return "login";
    }
}
