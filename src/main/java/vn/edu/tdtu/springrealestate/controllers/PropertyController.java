package vn.edu.tdtu.springrealestate.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.edu.tdtu.springrealestate.models.Property;
import vn.edu.tdtu.springrealestate.models.User;
import vn.edu.tdtu.springrealestate.services.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class PropertyController {
    @Autowired
    PropertyService propertyService;
    @Autowired
    UserService userService;
    @GetMapping("/property-list")
    public String getProperty(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        List<Property> properties = (List<Property>) propertyService.getAllProperty();
        model.addAttribute("properties", properties);
        return "property-list";
    }
    /*@GetMapping("/property-list")
    public String getProperty(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        User user = (User) userService.loadUserByUsername(username);
        if (username != null) {
            List<Property> properties = (List<Property>) propertyService.getPropertiesByUsername(username);
            model.addAttribute("properties", properties);
        }
        return "property-list";
    }*/
    @GetMapping("/property-list/{id}")
    public String getPropertyDetail(@PathVariable(value="id") Long id, Model model) {
        Property property = propertyService.findById(id);
        model.addAttribute("propertyDetail", property);
        return "property-detail";
    }
    @GetMapping("/property-search")
    public String searchProperties(@RequestParam("type") String type, @RequestParam("location") String location, Model model) {
        List<Property> properties = (List<Property>) propertyService.searchProperties(type,location);
        model.addAttribute("properties", properties);
        return "search-keywords";
    }
    @GetMapping("/property-detail")
    public String getPropertyDetail() {
        return "property-detail";
    }
    @GetMapping("/create-property")
    public String createProperty(Model model, HttpSession session) {
        if(session.getAttribute("token") == null) {
            return "redirect:/login";
        }
        Property property = new Property();
        model.addAttribute("property", property);
        return "create-property";
    }
    @PostMapping("/create-property")
    public String createProperty(HttpServletRequest request, HttpSession session) {
        String username = (String) session.getAttribute("username");
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedNow = now.format(formatter);
        User user = (User) userService.loadUserByUsername(username);

        Property property = new Property();
        property.setUser(user);
        property.setTitle(request.getParameter("title"));
        property.setType(request.getParameter("type"));
        property.setPurpose(request.getParameter("purpose"));
        property.setDescription(request.getParameter("description"));
        property.setAddress(request.getParameter("address"));
        property.setLocation(request.getParameter("location"));
        property.setPrice(Double.parseDouble(request.getParameter("price")));
        property.setArea(Double.parseDouble(request.getParameter("area")));
        property.setBedroom(Integer.parseInt(request.getParameter("bedroom")));
        property.setBathroom(Integer.parseInt(request.getParameter("bathroom")));
        property.setYear(Long.parseLong(request.getParameter("year")));
        property.setUploadDate(formattedNow);
        property.setContactPhone(request.getParameter("contactPhone"));
        propertyService.save(property);

        return "redirect:/yourHome";
    }
    @GetMapping("/saved-categories")
    public String savedCategories(Model model) {
        return "saved-categories";
    }
    @GetMapping("/payment")
    public String payment(Model model) {
        return "payment";
    }

}
