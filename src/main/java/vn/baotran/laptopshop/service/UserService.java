package vn.baotran.laptopshop.service;

import org.springframework.stereotype.Service;
import vn.baotran.laptopshop.domain.Role;
import vn.baotran.laptopshop.domain.User;
import vn.baotran.laptopshop.domain.dto.RegisterDto;
import vn.baotran.laptopshop.repository.OrderRepository;
import vn.baotran.laptopshop.repository.ProductRepository;
import vn.baotran.laptopshop.repository.RoleRepository;
import vn.baotran.laptopshop.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, ProductRepository productRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }


    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public List<User> getAllUserByEmail(String email) {
        return this.userRepository.findOneByEmail(email);
    }

    public User handleSaveUser(User user) {
        return this.userRepository.save(user);
    }

    public User getUserById(long id) {
        return this.userRepository.findById(id);
    }

    public void deleteAUser(Long id) {
        this.userRepository.deleteById(id);
    }

    public Role getRoleByName(String rollName) {
        return this.roleRepository.findByName(rollName);
    }

    public User registerDtoToUser(RegisterDto registerDto) {
        User user = new User();
        user.setFullName(registerDto.getFirstName() + " " + registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());
        return user;
    }

    public Boolean checkEmailExists(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public long countUsers() {
        return this.userRepository.count();
    }

    public long countProducts() {
        return this.productRepository.count();
    }

    public long countOrders() {
        return this.orderRepository.count();
    }
}
