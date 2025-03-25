package com.fpoly.asm.service.impl;

import com.fpoly.asm.mapper.ProductMapper;
import com.fpoly.asm.controller.request.ProductCreationRequest;
import com.fpoly.asm.controller.request.ProductUpdateRequest;
import com.fpoly.asm.controller.response.ProductPageResponse;
import com.fpoly.asm.controller.response.ProductResponse;
import com.fpoly.asm.entity.Category;
import com.fpoly.asm.entity.Product;
import com.fpoly.asm.exception.ResourceNotFoundException;
import com.fpoly.asm.repository.CategoryRepository;
import com.fpoly.asm.repository.ProductRepository;
import com.fpoly.asm.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "Product-Service")
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductPageResponse getAll(String keyword, String sort, int page, int size) {
        log.info("findAll start");

        Sort.Order order = determineSortOrder(sort);
        Pageable pageable = createPageable(page, size, order);
        Page<Product> entityPage = fetchProducts(keyword, pageable);

        return productMapper.toProductPageResponse(entityPage, page, size);
    }

    private Sort.Order determineSortOrder(String sort) {
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "id");
        if (StringUtils.hasLength(sort)) {
            Pattern pattern = Pattern.compile("^([a-zA-Z]+)(asc|desc)?$");
            Matcher matcher = pattern.matcher(sort);
            if (matcher.find()) {
                String columnName = matcher.group(1);
                Sort.Direction direction = matcher.group(2) != null && matcher.group(2).equalsIgnoreCase("desc")
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC;
                order = new Sort.Order(direction, columnName);
            }
        }
        return order;
    }

    private Pageable createPageable(int page, int size, Sort.Order order) {
        int pageNum = Math.max(page - 1, 0);
        return PageRequest.of(pageNum, size, Sort.by(order));
    }

    private Page<Product> fetchProducts(String keyword, Pageable pageable) {
        if (StringUtils.hasLength(keyword)) {
            keyword = "%" + keyword.toLowerCase() + "%";
            return productRepository.searchByKeyword(keyword, pageable);
        }
        return productRepository.findAll(pageable);
    }

    @Override
    public ProductResponse getById(Integer id) {
        log.info("findById {}", id);
        return productMapper.toProductResponse(
                getProduct(id));
    }

    @Override
    public List<Product> getByCategory(Integer categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public Product save(ProductCreationRequest request) {
        log.info("Saving product: {}", request);

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());

        return productRepository.save(product);
    }

    @Override
    public void update(ProductUpdateRequest request) {
        log.info("Updating product: {}", request);

        Product product = getProduct(request.getProductId());

        product.setLastUpdateTime(Date.valueOf(LocalDate.now()));
        if (request.getCategoryId() != null) {
            product.setCategory(getCategoryById(request.getCategoryId()));
        }        productMapper.updateProduct(product, request);

        log.info("Updated addresses: {}", request);
        productMapper.toProductResponse(productRepository.save(product));
    }

    @Override
    public void delete(Integer id) {
        log.info("Deleting product: {}", id);
        productRepository.deleteById(id);
        log.info("Deleted product: {}", id);
    }

    private Product getProduct(Integer id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    private Category getCategoryById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }
}
