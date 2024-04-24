package vn.edu.tdtu.springrealestate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.tdtu.springrealestate.models.AuthenticationResponse;
import vn.edu.tdtu.springrealestate.models.User;
import vn.edu.tdtu.springrealestate.service.AuthenticationService;

@RestController
public class TestController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/test/register")
    public ResponseEntity<String> register(@RequestBody User request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/test/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody User request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Hello to Test!!");
    }
}
