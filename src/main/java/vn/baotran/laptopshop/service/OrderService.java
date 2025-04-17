package vn.baotran.laptopshop.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.baotran.laptopshop.domain.Order;
import vn.baotran.laptopshop.domain.User;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Page<Order> fetchAllOrders(Pageable pageable);

    Optional<Order> fetchOrderById(Long id);

    void deleteOrderById(Long id);

    void updateOrder(Order order);

    List<Order> fetchOrderByUser(User user);

    void handlePlaceOrder(User user, HttpSession session,
                                 String receiverName, String receiverAddress, String receiverPhone);
}
