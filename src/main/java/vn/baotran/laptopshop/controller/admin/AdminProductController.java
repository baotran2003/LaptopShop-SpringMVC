package vn.baotran.laptopshop.controller.admin;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import vn.baotran.laptopshop.domain.Product;
import vn.baotran.laptopshop.service.ProductService;

import java.util.List;
import java.util.Optional;

@Controller
public class AdminProductController {
    private final ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/admin/product")
    public String getProduct(Model model, @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;
        try {
            if(pageOptional.isPresent()) {
                // convert from String -> Int
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (Exception ignored) {
            // page = 1
        }
        Pageable pageable = PageRequest.of(page - 1, 5);
        Page<Product> productPage = this.productService.fetchProducts(pageable);
        List<Product> products = productPage.getContent();

        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String getCreateProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("admin/product/create")
    public String handleCreateProduct(@ModelAttribute("newProduct") @Valid Product product,
                                      BindingResult newProductBindingResult,
                                      @RequestParam("fileName") MultipartFile file) {
        // validate
        if (newProductBindingResult.hasErrors()) {
            return "admin/product/create";
        }

        this.productService.createProduct(product, file);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/{id}")
    public String getProductDetailPage(Model model, @PathVariable Long id) {
        Product product = this.productService.fetchProductById(id).get();
        model.addAttribute("product", product);
        model.addAttribute("id", id);
        return "admin/product/detail";
    }

    @GetMapping("/admin/product/update/{id}")
    public String showUpdateProductPage(Model model, @PathVariable Long id) {
        Optional<Product> product = this.productService.fetchProductById(id);
        if (!product.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        model.addAttribute("newProduct", product.get());
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String handleUpdateProduct(@ModelAttribute("newProduct") @Valid Product product,
                                      BindingResult newProductBindingResult,
                                      @RequestParam("fileName") MultipartFile file) {
        // validate
        if (newProductBindingResult.hasErrors()) {
            return "admin/product/update";
        }
        this.productService.updateProduct(product, file);

        return "redirect:/admin/product";
    }


    @GetMapping("/admin/product/delete/{id}")
    public String getDeleteProductPage(Model model, @PathVariable Long id) {
        model.addAttribute("id", id);
        model.addAttribute("newProduct", new Product());
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String postDeleteProduct(Model model, @ModelAttribute("newProduct") Product product) {
        this.productService.deleteProduct(product.getId());
        return "redirect:/admin/product";
    }
}