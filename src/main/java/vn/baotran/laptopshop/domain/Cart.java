package vn.baotran.laptopshop.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "cart")
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 0)
    private Integer sum; // tổng số lượng sản phẩm trong giỏ hàng

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // cart_detail_id
    @OneToMany(mappedBy = "cart")
    List<CartDetail> cartDetails;
}
