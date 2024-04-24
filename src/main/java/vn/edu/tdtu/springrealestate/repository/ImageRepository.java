package vn.edu.tdtu.springrealestate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.tdtu.springrealestate.models.Image;
import vn.edu.tdtu.springrealestate.models.Property;

public interface ImageRepository extends JpaRepository<Image, Long> {
    public Iterable<Image> findByPropertyId(Property propertyId);
}
