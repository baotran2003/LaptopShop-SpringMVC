package vn.baotran.laptopshop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import vn.baotran.laptopshop.domain.Product;
import vn.baotran.laptopshop.domain.dto.ProductCriteriaDto;

import java.util.Optional;

public interface ProductService {
    Product createProduct(Product product, MultipartFile file);

    Product updateProduct(Product product, MultipartFile file);

    Page<Product> fetchProducts(Pageable pageable);

    Page<Product> fetchProductsWithSpec(Pageable page, ProductCriteriaDto productCriteriaDTO);

    Optional<Product> fetchProductById(Long id);

    void deleteProduct(Long id);
}
