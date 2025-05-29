package com.nicat.lightweightinventorystocktrackingsystem.controller;

import com.nicat.lightweightinventorystocktrackingsystem.model.dto.request.ProductAddRequest;
import com.nicat.lightweightinventorystocktrackingsystem.model.dto.request.ProductUpdateRequest;
import com.nicat.lightweightinventorystocktrackingsystem.model.dto.response.*;
import com.nicat.lightweightinventorystocktrackingsystem.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/products")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductService productService;

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public ProductAddResponse add(@RequestBody ProductAddRequest productAddRequest) {
        return productService.add(productAddRequest);
    }

    @PatchMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductUpdateResponse update(@PathVariable Long id,
                                        @RequestBody ProductUpdateRequest productUpdateRequest) {
        return productService.update(id, productUpdateRequest);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductGetResponse getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public Page<AllProductsResponse> getAll(Pageable pageable) {
        return productService.getAll(pageable);
    }

    @GetMapping("/products/by-category/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public List<AllProductsByCategoryResponse> getProductsByCategory(@PathVariable Long categoryId) {
        return productService.getProductsByCategory(categoryId);
    }
    @GetMapping("/products/stock-info")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductsStockInfoResponse> getStockInfo() {
        return productService.getStockInfo();
    }

}