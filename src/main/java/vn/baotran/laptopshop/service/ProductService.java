package vn.baotran.laptopshop.service;

import org.springframework.stereotype.Service;
import vn.baotran.laptopshop.domain.Cart;
import vn.baotran.laptopshop.domain.CartDetail;
import vn.baotran.laptopshop.domain.Product;
import vn.baotran.laptopshop.domain.User;
import vn.baotran.laptopshop.repository.CartDetailRepository;
import vn.baotran.laptopshop.repository.CartRepository;
import vn.baotran.laptopshop.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;

    public ProductService(ProductRepository productRepository, CartRepository cartRepository, CartDetailRepository cartDetailRepository, UserService userService) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
    }

    public Product createProduct(Product product) {
        return this.productRepository.save(product);
    }

    public List<Product> fetchProducts() {
        return this.productRepository.findAll();
    }

    public Optional<Product> fetchProductById(Long id) {
        return this.productRepository.findById(id);
    }

    public void deleteProduct(Long id) {
        this.productRepository.deleteById(id);
    }

    public void handleAddProductToCart(String email, Long productId) {
        User user = this.userService.getUserByEmail(email);
        if (user != null) {
            // check user co chua ?
            Cart cart = this.cartRepository.findByUser(user);
            if (cart == null) {
                // Create new Cart
                Cart otherCart = new Cart();
                otherCart.setUser(user);
                otherCart.setSum(1);

                cart = this.cartRepository.save(otherCart);
            }

            // save cart_detail , find productId
            Optional<Product> productOptional = this.productRepository.findById(productId);
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                CartDetail cartDetail = new CartDetail();

                cartDetail.setCart(cart);
                cartDetail.setProduct(product);
                cartDetail.setPrice(product.getPrice());
                cartDetail.setQuantity(1);

                // save cart_detail
                this.cartDetailRepository.save(cartDetail);
            }
        }
    }
}
