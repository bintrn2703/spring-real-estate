package vn.edu.tdtu.springrealestate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.tdtu.springrealestate.jwt.JwtService;
import vn.edu.tdtu.springrealestate.models.AuthenticationResponse;
import vn.edu.tdtu.springrealestate.models.User;
import vn.edu.tdtu.springrealestate.models.UserDto;
import vn.edu.tdtu.springrealestate.repository.UserRepository;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public String register(User request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        userRepository.save(user);
        return "User registered successfully";
    }

    public AuthenticationResponse authenticate(User request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()));
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        System.out.println(user.getUsername());
        //System.out.println(user.getPassword());
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

}
