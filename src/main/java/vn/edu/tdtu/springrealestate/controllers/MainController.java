package vn.edu.tdtu.springrealestate.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.edu.tdtu.springrealestate.models.AuthenticationResponse;
import vn.edu.tdtu.springrealestate.models.Role;
import vn.edu.tdtu.springrealestate.models.User;
import vn.edu.tdtu.springrealestate.models.UserDto;
import vn.edu.tdtu.springrealestate.repository.UserRepository;
import vn.edu.tdtu.springrealestate.services.AuthenticationService;

@Controller
public class MainController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationService authenticationService;
    public static final ThreadLocal<String> tokenHolder = new ThreadLocal<>();
    @GetMapping("/")
    public String index(HttpSession session) {
        if(session.getAttribute("token") == null) {
            return "redirect:/login";
        }

        return "index"; // display the home page
    }
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new UserDto());
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(HttpSession session, @RequestParam("username") String username, @RequestParam("password") String password) {
        User request = new User();
        request.setUsername(username);
        request.setPassword(password);
        AuthenticationResponse response = authenticationService.authenticate(request);
        if (response != null) {
            session.setAttribute("token", response.getToken());
            //tokenHolder.set(response.getToken());
            System.out.println("Login success");
            return "redirect:/"; // redirect to the home page
        } else {
            System.out.println("Login failed");
            return "login";
        }
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute("user") UserDto userDto) {
        if(!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            System.out.println("Password not match");
            return "redirect:/login";
        }
        if(userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            System.out.println("Username already exists");
            return "redirect:/login";
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setRole(Role.USER);
        authenticationService.register(user);
        return "login";
    }

    @GetMapping("/contact")
    public String contact(HttpSession session) {
        if(session.getAttribute("token") == null) {
            return "redirect:/login";
        }
        return "contact"; // display the contact page
    }
    @GetMapping("/about")
    public String about() {

        return "about";
    }
}
