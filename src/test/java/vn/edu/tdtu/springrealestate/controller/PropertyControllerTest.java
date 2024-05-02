package vn.edu.tdtu.springrealestate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import vn.edu.tdtu.springrealestate.models.Property;
import vn.edu.tdtu.springrealestate.models.User;
import vn.edu.tdtu.springrealestate.services.PropertyService;
import vn.edu.tdtu.springrealestate.services.UserService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
@AutoConfigureMockMvc
public class PropertyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private PropertyService propertyService;

    @Test
    public void testCreateProperty() throws Exception {
        // Tạo một phiên giả lập với username là "testUser"
        MockHttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute("username", "testUser");

        // Tạo một đối tượng User giả lập
        User mockUser = new User();
        mockUser.setUsername("testUser");

        // Mô phỏng hành vi của userService và propertyService
        when(userService.loadUserByUsername("testUser")).thenReturn(mockUser);
        when(propertyService.save(any(Property.class))).thenReturn(null);

        // Thực hiện yêu cầu POST đến "/create-property" và kiểm tra kết quả
        mockMvc.perform(post("/create-property")
                        .session(mockSession)
                        .param("title", "Test Title")
                        .param("type", "Test Type")
                        .param("purpose", "Test Purpose")
                        .param("description", "Test Description")
                        .param("address", "Test Address")
                        .param("location", "Test Location")
                        .param("price", "1000")
                        .param("area", "100")
                        .param("bedroom", "2")
                        .param("bathroom", "1")
                        .param("year", "2022")
                        .param("contactPhone", "1234567890"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/yourHome"));
    }


    @Test
    public void testGetPropertyDetail() throws Exception {
        // Tạo một đối tượng Property giả lập
        Property mockProperty = new Property();
        mockProperty.setTitle("Test Title");

        // Mô phỏng hành vi của propertyService
        when(propertyService.findById(1L)).thenReturn(mockProperty);

        // Thực hiện yêu cầu GET đến "/property-list/1" và kiểm tra kết quả
        mockMvc.perform(get("/property-list/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("property-detail"))
                .andExpect(model().attribute("propertyDetail", mockProperty));
    }

    @Test
    public void testSearchProperties() throws Exception {
        // Tạo một danh sách Property giả lập
        List<Property> mockProperties = Arrays.asList(new Property(), new Property());

        // Mô phỏng hành vi của propertyService
        when(propertyService.searchProperties("Test Type", "Test Location")).thenReturn(mockProperties);

        // Thực hiện yêu cầu GET đến "/property-search" với các tham số giả lập và kiểm tra kết quả
        mockMvc.perform(get("/property-search")
                        .param("type", "Test Type")
                        .param("location", "Test Location"))
                .andExpect(status().isOk())
                .andExpect(view().name("search-keywords"))
                .andExpect(model().attribute("properties", mockProperties));
    }
}
