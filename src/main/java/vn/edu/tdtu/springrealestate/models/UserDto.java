package vn.edu.tdtu.springrealestate.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Getter @Setter
public class UserDto {
    private String username;
    private String name;
    private String password;
    private String confirmPassword;
    private String email;

    public UserDto(String username, String name,String password, String confirmPassword, String email) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
    }
}
