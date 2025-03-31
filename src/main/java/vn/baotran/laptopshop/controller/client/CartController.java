package vn.baotran.laptopshop.controller.client;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.baotran.laptopshop.service.CartService;
import vn.baotran.laptopshop.util.CartUtil;


@Controller
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add-product-to-cart/{id}")
    public String addProductToCart(@PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String email = (String) session.getAttribute("email");
        this.cartService.handleAddProductToCart(email, id, session, 1);
        return "redirect:/";
    }

    @PostMapping("/add-product-from-view-detail")
    public String handleAddProductFromViewDetail(@RequestParam("id") Long id,
                                                 @RequestParam("quantity") Integer quantity,
                                                 HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String email = (String) session.getAttribute("email");
        this.cartService.handleAddProductToCart(email, id, session, quantity);
        return "redirect:/product/" + id;
    }

    @GetMapping("/cart")
    public String getCartPage(Model model, HttpServletRequest request) {
        CartUtil.loadCartDetailsIntoModel(request, model, cartService, true);
        return "client/cart/show";
    }

    @PostMapping("/delete-cart-product/{id}")
    public String deleteCartDetail(@PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        this.cartService.handleRemoveCartDetail(id, session);
        return "redirect:/cart";
    }
}