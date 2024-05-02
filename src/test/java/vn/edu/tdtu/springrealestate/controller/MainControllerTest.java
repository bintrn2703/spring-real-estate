package vn.edu.tdtu.springrealestate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import vn.edu.tdtu.springrealestate.models.*;
import vn.edu.tdtu.springrealestate.repository.CartRepository;
import vn.edu.tdtu.springrealestate.repository.UserRepository;
import vn.edu.tdtu.springrealestate.services.AuthenticationService;
import vn.edu.tdtu.springrealestate.services.PropertyService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.hibernate.query.sqm.tree.SqmNode.log;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private PropertyService propertyService;
    @MockBean
    private CartRepository cartRepository;

    @Test
    public void test() {
        log.info("Hello Test");
    }

    @Test
    public void testRegisterPost() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");
        userDto.setPassword("testPassword");
        userDto.setConfirmPassword("testPassword");
        userDto.setEmail("testEmail");

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setRole(Role.USER);

        when(userRepository.findByUsername(userDto.getUsername())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", userDto.getUsername())
                        .param("password", userDto.getPassword())
                        .param("confirmPassword", userDto.getConfirmPassword())
                        .param("email", userDto.getEmail()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("login"));

        verify(authenticationService, times(1)).register(user);
    }



    @Test
    public void testLoginPost() throws Exception {
        User user = new User();
        user.setUsername("phat");
        user.setPassword("1234");

        when(authenticationService.authenticate(any(User.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", user.getUsername())
                        .param("password", user.getPassword()))
                .andExpect(status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().string("Login failed"));

        verify(authenticationService, times(1)).authenticate(any(User.class));
    }



    @Test
    public void testYourHome() throws Exception {
        MockHttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute("username", "testUser");

        User mockUser = new User();
        mockUser.setUsername("testUser");

        List<Property> mockProperties = Arrays.asList(new Property(), new Property());

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(mockUser));
        when(propertyService.getPropertiesByUsername(mockUser)).thenReturn(mockProperties);

        mockMvc.perform(get("/yourHome").session(mockSession))
                .andExpect(status().isOk())
                .andExpect(view().name("saved-categories"))
                .andExpect(model().attribute("properties", mockProperties))
                .andExpect(model().attribute("other", true));
    }



    @Test
    public void testOtherHome() throws Exception {
        MockHttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute("username", "testUser");

        User mockUser = new User();
        mockUser.setUsername("testUser");

        List<Property> mockProperties = Arrays.asList(new Property(), new Property());
        List<Property> otherMockProperties = Arrays.asList(new Property(), new Property());

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(mockUser));
        when(propertyService.getPropertiesByUsername(mockUser)).thenReturn(mockProperties);
        when(cartRepository.findPropertiesByUser(mockUser)).thenReturn(otherMockProperties);

        mockMvc.perform(get("/otherHome").session(mockSession))
                .andExpect(status().isOk())
                .andExpect(view().name("saved-categories"))
                .andExpect(model().attribute("properties", mockProperties))
                .andExpect(model().attribute("otherProperties", otherMockProperties))
                .andExpect(model().attribute("other", false));
    }
}
