package vn.edu.tdtu.springrealestate.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class PropertyController {
    @GetMapping("/property-list")
    public String getProperty() {
        return "property-list";
    }
}
