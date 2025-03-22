package vn.baotran.laptopshop.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @NotNull
    @Min(value = 3, message = "Password must have at least 2 characters")
    private String password;

    @NotNull
    @Min(value = 3, message = "Full name must have at least 2 characters")
    private String fullName;

    @NotNull
    @NotEmpty(message = "Address cannot be empty")
    private String address;

    @NotNull
    @NotEmpty(message = "Phone number cannot be empty")
    private String phone;

    private String avatar;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;
}
