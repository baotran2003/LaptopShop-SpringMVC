package vn.baotran.laptopshop.controller.client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.baotran.laptopshop.domain.Product;
import vn.baotran.laptopshop.service.ProductService;

import java.util.List;

@Controller
public class HomePageController {
    private final ProductService productService;

    public HomePageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> productPage = this.productService.fetchProducts(pageable);
        List<Product> products = productPage.getContent();
        model.addAttribute("products", products);
        return "client/homepage/show";
    }
}
