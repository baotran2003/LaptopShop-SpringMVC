package vn.baotran.laptopshop.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.baotran.laptopshop.domain.Role;
import vn.baotran.laptopshop.domain.User;
import vn.baotran.laptopshop.domain.dto.RegisterDto;
import vn.baotran.laptopshop.repository.OrderRepository;
import vn.baotran.laptopshop.repository.ProductRepository;
import vn.baotran.laptopshop.repository.RoleRepository;
import vn.baotran.laptopshop.repository.UserRepository;
import vn.baotran.laptopshop.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UploadServiceImpl uploadService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ProductRepository productRepository, OrderRepository orderRepository, UploadServiceImpl uploadService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(User newUser, MultipartFile file) {
        // Upload file avatar
        String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");

        // Mã hóa mật khẩu
        String hashPassword = this.passwordEncoder.encode(newUser.getPassword());

        // Gán các thuộc tính cho user
        newUser.setAvatar(avatar);
        newUser.setPassword(hashPassword);
        newUser.setRole(this.getRoleByName(newUser.getRole().getName()));

        // Lưu user vào database
        this.handleSaveUser(newUser);
    }

    public void updateUser(User user, MultipartFile file) {
        User currentUser = this.getUserById(user.getId());
        if (currentUser != null) {
            currentUser.setAddress(user.getAddress());
            currentUser.setFullName(user.getFullName());
            currentUser.setPhone(user.getPhone());

            // update avatar nếu có file mới
            if (!file.isEmpty()) {
                String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
                currentUser.setAvatar(avatar);
            }

            // update role
            String roleName = user.getRole().getName();
            if (roleName.isEmpty()) {
                currentUser.setRole(this.getRoleByName(roleName));
            }
            this.handleSaveUser(currentUser);
        }
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return this.userRepository.findAll(pageable);
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
