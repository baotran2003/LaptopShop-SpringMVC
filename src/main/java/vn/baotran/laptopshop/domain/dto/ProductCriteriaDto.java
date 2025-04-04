package vn.baotran.laptopshop.domain.dto;

import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class ProductCriteriaDto {
    private Optional<String> page;

    private Optional<List<String>> factory;

    private Optional<List<String>> target;

    private Optional<List<String>> price;

    private Optional<String> sort;
}
