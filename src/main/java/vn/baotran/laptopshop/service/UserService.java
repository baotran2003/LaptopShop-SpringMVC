package vn.baotran.laptopshop.service;

import org.springframework.stereotype.Service;
import vn.baotran.laptopshop.domain.User;
import vn.baotran.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String handleHello() {
        return "Hello User Service.";
    }

    public User handleSaveUser(User user) {
        return this.userRepository.save(user);
    }
}
