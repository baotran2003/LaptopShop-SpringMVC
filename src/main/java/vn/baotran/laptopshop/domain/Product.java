package vn.baotran.laptopshop.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "products")
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
