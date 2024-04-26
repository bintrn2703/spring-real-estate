package vn.edu.tdtu.springrealestate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.edu.tdtu.springrealestate.models.Property;
import vn.edu.tdtu.springrealestate.repository.PropertyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class PropertyService {
    @Autowired
    PropertyRepository propertyRepository;

    public Iterable<Property> getAllProperty() {
        return propertyRepository.findAll();
    }
    public Property findById(Long id) {
        return propertyRepository.findById(id).orElse(null);
    }
    public Iterable<Property> getPropertiesByUsername(String username) {
        return propertyRepository.findByUserIdEmail(username);
    }
    public Property save(Property property) {
        return propertyRepository.save(property);
    }
    public Iterable<Property> searchProperties(String type, String location) {
        if ("All Property".equals(type) && "All location".equals(location)) {
            return propertyRepository.findAll();
        } else if (!"All Property".equals(type) && "All location".equals(location)) {
            return propertyRepository.findByType(type);
        } else if ("All Property".equals(type) && !"All location".equals(location)) {
            return propertyRepository.findByLocationContaining(location);
        } else {
            return propertyRepository.findByTypeAndLocation(type, location);
        }
    }
}
