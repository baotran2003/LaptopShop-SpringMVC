package vn.baotran.laptopshop.service.specification;

import org.springframework.data.jpa.domain.Specification;
import vn.baotran.laptopshop.domain.Product;
import vn.baotran.laptopshop.domain.Product_;

import java.util.List;

public class ProductSpecs {
    public static Specification<Product> nameLike(String name) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(root.get(Product_.NAME), "%" + name + "%");
    }

    // Case 1
    public static Specification<Product> minPrice(Double price) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.ge(root.get(Product_.PRICE), price);
    }

    // Case 2
    public static Specification<Product> maxPrice(Double price) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.le(root.get(Product_.PRICE), price);
    }

    // Case 3
    public static Specification<Product> matchFactory(String factory) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.get(Product_.FACTORY), factory);
    }

    // Case 4
    public static Specification<Product> matchListFactory(List<String> factory) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.in(root.get(Product_.FACTORY)).value(factory);
    }
    public static Specification<Product> matchListTarget(List<String> target) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.in(root.get(Product_.TARGET)).value(target);
    }

    // Case 5
    public static Specification<Product> matchPrice(Double minPrice, Double maxPrice) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.gt(root.get(Product_.PRICE), minPrice),
                        criteriaBuilder.le(root.get(Product_.PRICE), maxPrice)
                );
    }

    // Case 6
    public static Specification<Product> matchMultiplePrice(Double minPrice, Double maxPrice) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get(Product_.PRICE), minPrice, maxPrice);
    }
}
