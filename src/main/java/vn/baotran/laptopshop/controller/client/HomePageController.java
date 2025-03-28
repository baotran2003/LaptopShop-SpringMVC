package vn.baotran.laptopshop.controller.client;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import vn.baotran.laptopshop.domain.Order;
import vn.baotran.laptopshop.domain.Product;
import vn.baotran.laptopshop.domain.User;
import vn.baotran.laptopshop.domain.dto.RegisterDto;
import vn.baotran.laptopshop.service.OrderService;
import vn.baotran.laptopshop.service.ProductService;
import vn.baotran.laptopshop.service.UserService;
import vn.baotran.laptopshop.util.CartUtil;

import java.util.List;

@Controller
public class HomePageController {
    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;
    private final PasswordEncoder passwordEncoder;

    public HomePageController(ProductService productService, UserService userService, OrderService orderService, PasswordEncoder passwordEncoder) {
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        List<Product> products = this.productService.fetchProducts();
        model.addAttribute("products", products);
        return "client/homepage/show";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerUser", new RegisterDto());
        return "client/auth/register";
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("registerUser") @Valid RegisterDto registerDto,
                                 BindingResult bindingResult) {

        // Kiểm tra nếu có lỗi validation
        if (bindingResult.hasErrors()) {
            return "client/auth/register";
        }

        User user = this.userService.registerDtoToUser(registerDto);
        String hashPassword = this.passwordEncoder.encode(user.getPassword());

        user.setPassword(hashPassword);
        user.setRole(this.userService.getRoleByName("USER"));

        this.userService.handleSaveUser(user);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        return "client/auth/login";
    }

    @GetMapping("/access-deny")
    public String getDenyPage(Model model) {
        return "client/auth/deny";
    }

    @GetMapping("/order-history")
    public String getOrderHistoryPage(Model model, HttpServletRequest request) {
        User currentUser = CartUtil.getCurrentUserFromSession(request);
        List<Order> orders = this.orderService.fetchOrderByUser(currentUser);
        model.addAttribute("orders", orders);
        return "client/cart/order_history";
    }
}
