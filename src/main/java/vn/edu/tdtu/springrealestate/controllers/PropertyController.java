package vn.edu.tdtu.springrealestate.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class PropertyController {
    @GetMapping("/property-list")
    public String getProperty() {
        return "property-list";
    }
}
