package vn.edu.tdtu.springrealestate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.tdtu.springrealestate.models.Property;
import vn.edu.tdtu.springrealestate.repository.PropertyRepository;
import java.util.List;

@Service
public class PropertyService {
    @Autowired
    PropertyRepository propertyRepository;

    public Iterable<Property> getAllProperty() {

        return propertyRepository.findAll();
    }
    // find by username
    public Iterable<Property> getPropertiesByUsername(String username) {
        return propertyRepository.findByUserIdEmail(username);
    }
    public Property save(Property property) {
        return propertyRepository.save(property);
    }
}
