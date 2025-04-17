package vn.baotran.laptopshop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import vn.baotran.laptopshop.domain.Role;
import vn.baotran.laptopshop.domain.User;
import vn.baotran.laptopshop.domain.dto.RegisterDto;

import java.util.List;

public interface UserService {
    void createUser(User newUser, MultipartFile file);

    void updateUser(User user, MultipartFile file);

    Page<User> getAllUsers(Pageable pageable);

    List<User> getAllUserByEmail(String email);

    User handleSaveUser(User user);

    User getUserById(long id);

    void deleteAUser(Long id);

    Role getRoleByName(String rollName);

    User registerDtoToUser(RegisterDto registerDto);

    Boolean checkEmailExists(String email);

    User getUserByEmail(String email);

    long countUsers();

    long countProducts();

    long countOrders();
}
