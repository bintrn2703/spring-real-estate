package vn.edu.tdtu.springrealestate.controller;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
import vn.edu.tdtu.springrealestate.controllers.MainController;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MainController mainController;


    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private PropertyService propertyService;
    @MockBean
    private CartRepository cartRepository;


    @Test
    void testRegisterPost_WithValidUserDto_ReturnsLogin() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");
        userDto.setPassword("password");
        userDto.setConfirmPassword("password");
        userDto.setEmail("test@example.com");
        userDto.setName("Test User");

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.empty());

        // Act
        String result = mainController.registerPost(userDto);

        // Assert
        assertEquals("login", result);
        verify(authenticationService, times(1)).register(any(User.class));
    }

    @Test
    void testRegisterPost_WithMismatchedPasswords_ReturnsRedirectToLogin() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");
        userDto.setName("Test User");
        userDto.setPassword("password");
        userDto.setConfirmPassword("differentPassword"); // Mismatched password


        // Act
        String result = mainController.registerPost(userDto);

        // Assert
        assertEquals("redirect:/login", result);
    }

    @Test
    void testRegisterPost_WithExistingUsername_ReturnsRedirectToLogin() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setUsername("existingUser");
        userDto.setName("Test User");
        userDto.setPassword("password");
        userDto.setConfirmPassword("password");
        userDto.setEmail("test@example.com");

        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(new User()));

        // Act
        String result = mainController.registerPost(userDto);

        // Assert
        assertEquals("redirect:/login", result);
    }



//    @Test
//    void testLoginPost_WithValidCredentials_ReturnsRedirectToYourHome() {
//        // Arrange
//        HttpSession session = mock(HttpSession.class);
//        String username = "testUser";
//        String password = "password";
//        User request = new User();
//        request.setUsername(username);
//        request.setPassword(password);
//        AuthenticationResponse response = new AuthenticationResponse("validToken");
//
//        when(authenticationService.authenticate(eq(request))).thenReturn(response);
//
//        // Act
//        String result = mainController.loginPost(session, username, password);
//
//        // Assert
//        assertEquals("redirect:/yourHome", result);
//        verify(session, times(1)).setAttribute("token", "validToken");
//        verify(session, times(1)).setAttribute("username", username);
//        verify(authenticationService, times(1)).authenticate(request);
//    }
//
//    @Test
//    void testLoginPost_WithInvalidCredentials_ReturnsLogin() {
//        // Arrange
//        HttpSession session = mock(HttpSession.class);
//        String username = "testUser";
//        String password = "wrongPassword";
//        User request = new User();
//        request.setUsername(username);
//        request.setPassword(password);
//
//        when(authenticationService.authenticate(request)).thenReturn(null);
//
//        // Act
//        String result = mainController.loginPost(session, username, password);
//
//        // Assert
//        assertEquals("login", result);
//        verify(session, never()).setAttribute(anyString(), any());
//        verify(authenticationService, times(1)).authenticate(request);
//    }



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
