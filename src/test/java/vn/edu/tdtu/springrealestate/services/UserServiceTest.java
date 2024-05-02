package vn.edu.tdtu.springrealestate.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import vn.edu.tdtu.springrealestate.models.User;
import vn.edu.tdtu.springrealestate.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testLoadUserByUsername() {
        // Tạo một đối tượng User giả lập
        User mockUser = new User();
        mockUser.setUsername("testUser");

        // Mô phỏng hành vi của userRepository
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(mockUser));

        // Gọi phương thức loadUserByUsername() và kiểm tra kết quả
        UserDetails userDetails = userService.loadUserByUsername("testUser");
        assertEquals("testUser", userDetails.getUsername());
    }

    @Test
    public void testLoadUserByUsername_NotFound() {
        // Mô phỏng hành vi của userRepository
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        // Gọi phương thức loadUserByUsername() và kiểm tra ngoại lệ
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("testUser"));
    }
}
