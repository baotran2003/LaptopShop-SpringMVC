package vn.baotran.laptopshop.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.baotran.laptopshop.domain.*;
import vn.baotran.laptopshop.repository.*;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    private final UploadService uploadService;


    public ProductService(ProductRepository productRepository, UploadService uploadService) {
        this.productRepository = productRepository;

        this.uploadService = uploadService;
    }

    public Product createProduct(Product product, MultipartFile file) {
        // Upload file và gán image nếu file không rỗng
        if (file != null && !file.isEmpty()) {
            String image = this.uploadService.handleSaveUploadFile(file, "product");
            product.setImage(image);
        } else {
            throw new IllegalArgumentException("Product image is required");
        }

        // Lưu sản phẩm vào database
        return this.productRepository.save(product);
    }

    public Product updateProduct(Product product, MultipartFile file) {
        Optional<Product> currentProductOpt = this.fetchProductById(product.getId());
        if (currentProductOpt.isEmpty()) {
            throw new IllegalArgumentException("Product with ID " + product.getId() + " not found");
        }

        Product currentProduct = currentProductOpt.get();
        // Cập nhật hình ảnh nếu có file mới
        if (file != null && !file.isEmpty()) {
            String img = this.uploadService.handleSaveUploadFile(file, "product");
            currentProduct.setImage(img);
        }

        // Cập nhật các thuộc tính khác
        currentProduct.setName(product.getName());
        currentProduct.setPrice(product.getPrice());
        currentProduct.setQuantity(product.getQuantity());
        currentProduct.setDetailDesc(product.getDetailDesc());
        currentProduct.setShortDesc(product.getShortDesc());
        currentProduct.setFactory(product.getFactory());
        currentProduct.setTarget(product.getTarget());

        // Lưu sản phẩm đã cập nhật
        return this.productRepository.save(currentProduct);
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

}
