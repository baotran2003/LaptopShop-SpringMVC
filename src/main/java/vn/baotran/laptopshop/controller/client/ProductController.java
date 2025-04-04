package vn.baotran.laptopshop.controller.client;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import vn.baotran.laptopshop.domain.Product;
import vn.baotran.laptopshop.domain.Product_;
import vn.baotran.laptopshop.domain.dto.ProductCriteriaDto;
import vn.baotran.laptopshop.service.ProductService;

import java.util.ArrayList;
import java.util.List;


@Controller
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String getProductPage(Model model, ProductCriteriaDto productCriteriaDTO, HttpServletRequest request) {
        int page = 1;
        try {
            if (productCriteriaDTO.getPage().isPresent()) {
                // convert from String to int
                page = Integer.parseInt(productCriteriaDTO.getPage().get());
            }
        } catch (Exception e) {
            // page = 1
            // TODO: handle exception
        }

        Pageable pageable = PageRequest.of(page - 1, 6);

        // check sort price
        if (productCriteriaDTO.getSort() != null && productCriteriaDTO.getSort().isPresent()) {
            String sort = productCriteriaDTO.getSort().get();
            if (sort.equals("gia-tang-dan")) {
                pageable = PageRequest.of(page - 1, 6, Sort.by(Product_.PRICE).ascending());
            } else if (sort.equals("gia-giam-dan")) {
                pageable = PageRequest.of(page - 1, 6, Sort.by(Product_.PRICE).descending());
            }
        }

        Page<Product> productPage = this.productService.fetchProductsWithSpec(pageable, productCriteriaDTO);

        List<Product> products = productPage.getContent().size() > 0 ? productPage.getContent() : new ArrayList<Product>();

        String qs = request.getQueryString();
        if (qs != null && !qs.isBlank()) {
            qs = qs.replace("page=" + page, "");
        }
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("queryString", qs);
        model.addAttribute("totalPages", productPage.getTotalPages());
        return "client/product/show";
    }

    @GetMapping("/product/{id}")
    public String getProductPageDetail(Model model, @PathVariable Long id) {
        Product product = this.productService.fetchProductById(id).get();
        model.addAttribute("product", product);
        model.addAttribute("id", id);
        return "client/product/detail";
    }
}