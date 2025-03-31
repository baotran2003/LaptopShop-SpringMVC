package vn.baotran.laptopshop.controller.client;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.baotran.laptopshop.domain.Cart;
import vn.baotran.laptopshop.domain.CartDetail;
import vn.baotran.laptopshop.domain.Order;
import vn.baotran.laptopshop.domain.User;
import vn.baotran.laptopshop.service.CartService;
import vn.baotran.laptopshop.service.OrderService;
import vn.baotran.laptopshop.util.CartUtil;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;

    public OrderController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @GetMapping("/checkout")
    public String getCheckOutPage(Model model, HttpServletRequest request) {
        CartUtil.loadCartDetailsIntoModel(request, model, cartService, false);
        return "client/cart/checkout";
    }

    @PostMapping("/confirm-checkout")
    public String confirmCheckout(@ModelAttribute("cart") Cart cart) {
        List<CartDetail> cartDetails = cart == null ? new ArrayList<>() : cart.getCartDetails();
        this.cartService.handleUpdateCartBeforeCheckout(cartDetails);
        return "redirect:/checkout";
    }

    @PostMapping("/place-order")
    public String handlePlaceOrder(HttpServletRequest request,
                                   @RequestParam("receiverName") String receiverName,
                                   @RequestParam("receiverAddress") String receiverAddress,
                                   @RequestParam("receiverPhone") String receiverPhone) {
        User currentUser = CartUtil.getCurrentUserFromSession(request);
        HttpSession session = request.getSession(false);
        this.orderService.handlePlaceOrder(currentUser, session, receiverName, receiverAddress, receiverPhone);
        return "redirect:/thanks";
    }

    @GetMapping("/order-history")
    public String getOrderHistoryPage(Model model, HttpServletRequest request) {
        User currentUser = CartUtil.getCurrentUserFromSession(request);
        List<Order> orders = this.orderService.fetchOrderByUser(currentUser);
        model.addAttribute("orders", orders);
        return "client/cart/order_history";
    }

    @GetMapping("/thanks")
    public String getThankPage(Model model) {
        return "client/cart/thanks";
    }
}