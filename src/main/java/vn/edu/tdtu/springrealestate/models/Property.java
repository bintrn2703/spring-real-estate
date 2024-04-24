package vn.edu.tdtu.springrealestate.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "property")
@NoArgsConstructor
@Getter
@Setter
public class Property {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
    private String title;
    private String type;
    private String purpose;
    private String description;
    private String address;
    private String location;
    private Double price;
    private Double area;
    private Integer bedroom;
    private Integer bathroom;
    private String status;
    @Column(name = "thumbnail_image")
    private String thumbnailImage;

    public Property(Long id, User userId, String title, String type, String purpose, String description, String address, String location, Double price, Double area, Integer bedroom, Integer bathroom, String status) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.type = type;
        this.purpose = purpose;
        this.description = description;
        this.address = address;
        this.location = location;
        this.price = price;
        this.area = area;
        this.bedroom = bedroom;
        this.bathroom = bathroom;
        this.status = status;
    }
}