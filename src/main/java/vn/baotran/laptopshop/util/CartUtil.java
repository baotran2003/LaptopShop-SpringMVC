package vn.baotran.laptopshop.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import vn.baotran.laptopshop.domain.Cart;
import vn.baotran.laptopshop.domain.CartDetail;
import vn.baotran.laptopshop.domain.User;
import vn.baotran.laptopshop.service.CartService;

import java.util.ArrayList;
import java.util.List;

public class CartUtil {
    public static void loadCartDetailsIntoModel(HttpServletRequest request, Model model, CartService cartService, boolean includeCart) {
        User currentUser = getCurrentUserFromSession(request); // Lấy User từ session
        Cart cart = cartService.fetchByUser(currentUser); // Lấy giỏ hàng từ ProductService
        List<CartDetail> cartDetails = (cart == null) ? new ArrayList<>() : cart.getCartDetails();
        double totalPrice = 0;
        for (CartDetail cartDetail : cartDetails) {
            totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
        }
        model.addAttribute("cartDetails", cartDetails); // Thêm chi tiết giỏ hàng vào model
        model.addAttribute("totalPrice", totalPrice); // Thêm tổng giá vào model
        if (includeCart) { // Nếu cần thêm đối tượng Cart
            model.addAttribute("cart", cart); // Thêm Cart vào model
        }
    }

    public static User getCurrentUserFromSession(HttpServletRequest request) {
        User currentUser = new User();
        HttpSession session = request.getSession(false);
        if (session != null) {
            Long id = (Long) session.getAttribute("id");
            currentUser.setId(id);
        }
        return currentUser;
    }
}