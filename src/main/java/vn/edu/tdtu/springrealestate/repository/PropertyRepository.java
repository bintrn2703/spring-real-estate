package vn.edu.tdtu.springrealestate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.tdtu.springrealestate.models.Property;
import vn.edu.tdtu.springrealestate.models.User;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long>, JpaSpecificationExecutor<Property> {
   // public Iterable<Property> findByUserIdEmail(String email);
    public Iterable<Property> findByTitleContaining(String title);
    public Iterable<Property> findByPriceBetween(double min, double max);
    public Iterable<Property> findByAreaBetween(double min, double max);
    public Iterable<Property> findByType(String type);
    public Iterable<Property> findByStatus(String status);
    public Iterable<Property> findByLocationContaining(String location);
    public Iterable<Property> findByDescriptionContaining(String description);
    public Iterable<Property> findByTypeAndLocation(String type, String location);
    public Iterable<Property> findByUserUsername(String username);
    public Iterable<Property> findByUserIdAndIsSavedTrue(Long id);
}
