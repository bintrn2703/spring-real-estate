package vn.edu.tdtu.springrealestate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.tdtu.springrealestate.models.Property;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    public Iterable<Property> findByUserIdEmail(String email);
    public Iterable<Property> findByTitleContaining(String title);
    public Iterable<Property> findByPriceBetween(double min, double max);
    public Iterable<Property> findByAreaBetween(double min, double max);
    public Iterable<Property> findByType(String type);
    public Iterable<Property> findByStatus(String status);
    public Iterable<Property> findByLocationContaining(String location);
    public Iterable<Property> findByDescriptionContaining(String description);

}
