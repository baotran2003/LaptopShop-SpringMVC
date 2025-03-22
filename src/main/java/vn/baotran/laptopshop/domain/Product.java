package vn.baotran.laptopshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "products")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private Double price;

    private String image;

    private String detailDesc;

    private String shortDesc;

    private Long quantity;

    private Long sold;

    private String factory;

    private String target;
}
