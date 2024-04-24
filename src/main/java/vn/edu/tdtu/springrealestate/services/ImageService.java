package vn.edu.tdtu.springrealestate.services;

import vn.edu.tdtu.springrealestate.models.Image;
import vn.edu.tdtu.springrealestate.models.Property;
import vn.edu.tdtu.springrealestate.repository.ImageRepository;

public class ImageService {
    ImageRepository imageRepository;

    public Iterable<Image> findByPropertyId(Property propertyId) {
        return imageRepository.findByPropertyId(propertyId);
    }
    public Image findById(Long id) {
        return imageRepository.findById(id).orElse(null);
    }
    public Image getThumbnail(Property property) {
        return imageRepository.findByPropertyId(property).iterator().next();
    }
    public Image save(Image image) {
        return imageRepository.save(image);
    }
}
