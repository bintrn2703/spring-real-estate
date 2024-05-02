package vn.edu.tdtu.springrealestate.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.tdtu.springrealestate.models.*;
import vn.edu.tdtu.springrealestate.repository.CartRepository;
import vn.edu.tdtu.springrealestate.repository.PropertyRepository;
import vn.edu.tdtu.springrealestate.repository.UserRepository;
import vn.edu.tdtu.springrealestate.services.AuthenticationService;
import vn.edu.tdtu.springrealestate.services.PropertyService;
import vn.edu.tdtu.springrealestate.services.UserService;

import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private UserService userService;
    @Autowired
    private CartRepository cartRepository;
    public static final ThreadLocal<String> tokenHolder = new ThreadLocal<>();
    @GetMapping("/")
    public String index(HttpSession session) {
        /*if(session.getAttribute("token") == null) {
            return "redirect:/login";
        }*/
        return "index-unlogin"; // display the home page
    }

    @GetMapping("/home")
    public String home(HttpSession session) {
        if (session.getAttribute("token") == null) {
            return "redirect:/";
        }
        return "index";
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
            session.setAttribute("username", username);
            System.out.println("Login success");
            //return "redirect:/home"; // redirect to the home page
            return "redirect:/yourHome";
        } else {
            System.out.println("Login failed");
            return "login";
        }
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> loginPost(HttpSession session, @RequestParam("username") String username, @RequestParam("password") String password) {
//        User request = new User();
//        request.setUsername(username);
//        request.setPassword(password);
//        AuthenticationResponse response = authenticationService.authenticate(request);
//        if (response != null) {
//            session.setAttribute("token", response.getToken());
//            session.setAttribute("username", username);
//            System.out.println("Login success");
//            return ResponseEntity.ok("Login success");
//        } else {
//            System.out.println("Login failed");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
//        }
//    }

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
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setRole(Role.USER);
        authenticationService.register(user);
        return "login";
    }

    @GetMapping("/contact")
    public String contact(HttpSession session) {
        /*if(session.getAttribute("token") == null) {
            return "redirect:/login";
        }*/
        return "contact"; // display the contact page
    }
    @GetMapping("/about")
    public String about() {

        return "about";
    }
    @GetMapping("/property-type")
    public String propertyType() {

        return "property-type";
    }
    @GetMapping("/property-agent")
    public String propertyAgent() {

        return "property-agent";
    }
//    @GetMapping("/property-search")
//    public String Search() {
//        return "search-keywords";
//    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/yourHome")
    public String yourHome(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        //String username = userService.getUsernameFromToken();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<Property> properties = (List<Property>) propertyService.getPropertiesByUsername(user);
            model.addAttribute("properties", properties);
            model.addAttribute("other", true);
        } else {
            return "saved-categories";
        }
        return "saved-categories";
    }

    @GetMapping("/otherHome")
    public String otherHome(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        //String username = userService.getUsernameFromToken();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<Property> properties = (List<Property>) propertyService.getPropertiesByUsername(user);
            model.addAttribute("properties", properties);

            List<Property> otherProperties = cartRepository.findPropertiesByUser(user);
            model.addAttribute("otherProperties", otherProperties);
            model.addAttribute("other", false);

        } else {
            return "saved-categories";
        }
        return "saved-categories";
    }

    @GetMapping("/property-list/{id}/saveHome")
    public String saveHome(@PathVariable(value = "id") Long id, HttpSession session) {
        //String username = (String) session.getAttribute("username");


        Property property = propertyService.findById(id);
        Cart cart = new Cart();
        cart.setProperty(property);
        cart.setUser(userRepository.findByUsername("phat").orElseThrow());
        cartRepository.save(cart);
        return "redirect:/property-list/" + id;
    }


}
