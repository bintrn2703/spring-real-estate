package vn.edu.tdtu.springrealestate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.tdtu.springrealestate.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
