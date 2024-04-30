package vn.edu.tdtu.springrealestate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.tdtu.springrealestate.models.Cart;
import vn.edu.tdtu.springrealestate.models.Property;
import vn.edu.tdtu.springrealestate.models.User;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c.property FROM Cart c WHERE c.user = :user")
    List<Property> findPropertiesByUser(User user);
}
