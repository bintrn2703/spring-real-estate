package vn.edu.tdtu.springrealestate.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import vn.edu.tdtu.springrealestate.models.Property;
import vn.edu.tdtu.springrealestate.repository.PropertyRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PropertyServiceTest {
    @Autowired
    private PropertyService propertyService;

    @MockBean
    private PropertyRepository propertyRepository;

    @Test
    public void testSearchProperties() {
        // Tạo một danh sách Property giả lập
        List<Property> mockProperties = Arrays.asList(new Property(), new Property());

        // Mô phỏng hành vi của propertyRepository
        when(propertyRepository.findAll()).thenReturn(mockProperties);
        when(propertyRepository.findByType(anyString())).thenReturn(mockProperties);
        when(propertyRepository.findByLocationContaining(anyString())).thenReturn(mockProperties);
        when(propertyRepository.findByTypeAndLocation(anyString(), anyString())).thenReturn(mockProperties);

        // Gọi phương thức searchProperties() và kiểm tra kết quả
        Iterable<Property> properties = propertyService.searchProperties("All Property", "All location");
        assertEquals(mockProperties, properties);

        properties = propertyService.searchProperties("Test Type", "All location");
        assertEquals(mockProperties, properties);

        properties = propertyService.searchProperties("All Property", "Test Location");
        assertEquals(mockProperties, properties);

        properties = propertyService.searchProperties("Test Type", "Test Location");
        assertEquals(mockProperties, properties);
    }
}
