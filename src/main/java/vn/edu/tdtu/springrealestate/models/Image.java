package vn.edu.tdtu.springrealestate.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "images")
@NoArgsConstructor
@Getter
@Setter
public class Image {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property propertyId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
    private String description;
    private String url;

    public Image(Long id, Property propertyId, User userId, String description, String url) {
        this.id = id;
        this.propertyId = propertyId;
        this.userId = userId;
        this.description = description;
        this.url = url;
    }
}
