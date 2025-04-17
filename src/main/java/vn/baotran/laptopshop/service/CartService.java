package vn.baotran.laptopshop.service;

import jakarta.servlet.http.HttpSession;
import vn.baotran.laptopshop.domain.Cart;
import vn.baotran.laptopshop.domain.CartDetail;
import vn.baotran.laptopshop.domain.User;

import java.util.List;

public interface CartService {
    void handleAddProductToCart(String email, Long productId, HttpSession session, Integer quantity);

    Cart fetchByUser(User user);

    void handleRemoveCartDetail(Long cartDetailId, HttpSession session);

    void handleUpdateCartBeforeCheckout(List<CartDetail> cartDetails);
}
