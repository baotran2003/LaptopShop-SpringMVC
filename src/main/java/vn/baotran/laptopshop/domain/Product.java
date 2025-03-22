package vn.baotran.laptopshop.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty(message = "Product name not empty")
    private String name;

    @NotNull
    @DecimalMin(value = "0", inclusive = false, message = "Price must be greater than 0 ")
    private Double price;

    private String image;

    @NotNull
    @NotEmpty(message = "DetailDesc not empty")
    @Column(columnDefinition = "MEDIUMTEXT")
    private String detailDesc;

    @NotNull
    @NotEmpty(message = "ShortDesc not empty")
    private String shortDesc;

    @NotNull
    @DecimalMin(value = "0", inclusive = false, message = "Quantity must be greater than 0 ")
    private Long quantity;

    private Long sold;

    private String factory;

    private String target;
}
