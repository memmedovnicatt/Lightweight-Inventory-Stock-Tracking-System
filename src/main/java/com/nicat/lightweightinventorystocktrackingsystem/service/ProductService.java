package com.nicat.lightweightinventorystocktrackingsystem.service;

import com.nicat.lightweightinventorystocktrackingsystem.dao.entity.Category;
import com.nicat.lightweightinventorystocktrackingsystem.dao.entity.Product;
import com.nicat.lightweightinventorystocktrackingsystem.dao.repository.CategoryRepository;
import com.nicat.lightweightinventorystocktrackingsystem.dao.repository.ProductRepository;
import com.nicat.lightweightinventorystocktrackingsystem.mapper.ProductMapper;
import com.nicat.lightweightinventorystocktrackingsystem.model.dto.request.ProductAddRequest;
import com.nicat.lightweightinventorystocktrackingsystem.model.dto.request.ProductUpdateRequest;
import com.nicat.lightweightinventorystocktrackingsystem.model.dto.response.*;
import com.nicat.lightweightinventorystocktrackingsystem.model.enums.Status;
import com.nicat.lightweightinventorystocktrackingsystem.model.exception.child.AlreadyDeletedException;
import com.nicat.lightweightinventorystocktrackingsystem.model.exception.child.NotFoundException;
import com.nicat.lightweightinventorystocktrackingsystem.util.BarcodeGenerateService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
    BarcodeGenerateService barcodeGenerateService;
    ProductRepository productRepository;
    ProductMapper productMapper;
    CategoryRepository categoryRepository;

    public ProductAddResponse add(ProductAddRequest productAddRequest) {
        log.info("add method was started");
        log.info("{}", productAddRequest);

        Category category = categoryRepository.findByNameAndStatus(productAddRequest.getCategoryName(), Status.ACTIVE)
                .orElseThrow(() -> new NotFoundException("Category wasn't found"));
        log.info("category was found");
        String barcode = barcodeGenerateService.generateBarcode();
        String barcodeTimestamp = barcodeGenerateService.generateBarcodeTimestamp();
        Product product = new Product();
        log.info("product successfully created");
        product.setCategory(category);
        product.setCreatedAt(LocalDateTime.now());
        product.setBarcode(barcode);
        product.setGenerateBarcodeTimestamp(barcodeTimestamp);
        product.setName(productAddRequest.getName());
        product.setStatus(Status.ACTIVE);
        product.setCurrency(productAddRequest.getCurrency());
        product.setPrice(productAddRequest.getPrice());

        productRepository.save(product);
        log.info("product successfully saved in database");
        ProductAddResponse productAddResponse = new ProductAddResponse();
        productAddResponse.setId(product.getId());
        log.info("id successfully set to productAddResponse");
        return productAddResponse;
    }

    public ProductUpdateResponse update(Long id, ProductUpdateRequest productUpdateRequest) {
        log.info("update method was started");
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product id : " + id + " was not found"));
        log.info("Product id : {}", id + " was found");

        Category category = categoryRepository.findByNameAndStatus(productUpdateRequest.getNewCategoryName(), Status.ACTIVE)
                .orElseThrow(() -> new NotFoundException("Category wasn't found cause category name format or DELETED status"));
        log.info("category name : {} was found", productUpdateRequest.getNewCategoryName());
        productMapper.toUpdateProduct(productUpdateRequest, product);
        product.setUpdateAt(LocalDateTime.now());
        product.setCategory(category);
        productRepository.save(product);
        log.info("Product was successfully saved in database");
        ProductUpdateResponse productUpdateResponse = new ProductUpdateResponse();
        productUpdateResponse.setNewCurrency(product.getCurrency());
        productUpdateResponse.setNewName(product.getName());
        productUpdateResponse.setNewPrice(product.getPrice());
        productUpdateResponse.setNewStatus(product.getStatus());
        log.info("ProductUpdateResponse successfully returned");
        return productUpdateResponse;
    }

    public void delete(Long id) {
        log.info("delete method was started");
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product id : " + id + " was not found"));
        log.info("id was found");
        if (product.getStatus() == Status.DELETED) {
            throw new AlreadyDeletedException("Product is already deleted status");
        }
        product.setStatus(Status.DELETED);
        product.setDeletedAt(LocalDateTime.now());
        log.info("Status and deletedAt successfully set to Product");
        productRepository.save(product);
        log.info("Product was successfully deleted");
    }


    public ProductGetResponse getById(Long id) {
        log.info("getById method was started");
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product id : " + id + " wasn't found"));
        return productMapper.toProductGetResponse(product);
    }

    public Page<AllProductsResponse> getAll(Pageable pageable) {
        log.info("getAll method was started for product");
        Page<Product> productPage = productRepository.findByStatus(pageable, Status.ACTIVE);
        return productPage.map(productMapper::toAllProductsResponse);
    }

    public List<AllProductsByCategoryResponse> getProductsByCategory(Long categoryId) {
        log.info("getProductsByCategory method was started");

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("This categoryId was not found"));
        log.info("categoryId : {} was found", category.getId());
        List<Product> products = productRepository.findByCategoryId(categoryId);
        if (products.isEmpty()) {
            throw new NotFoundException("Products are not found");
        }
        log.info("Product was found");
        List<AllProductsByCategoryResponse> responseList = new ArrayList<>();
        for (Product product : products) {
            AllProductsByCategoryResponse response = new AllProductsByCategoryResponse();
            response.setPrice(product.getPrice());
            response.setName(product.getName());
            response.setCurrency(product.getCurrency());
            responseList.add(response);
        }
        return responseList;
    }

    public List<ProductsStockInfoResponse> getStockInfo() {
        log.info("getStockInfo method was started for stock service");
        List<Product> products = productRepository.findAllByStatus(Status.ACTIVE);
        if (products.isEmpty()) {
            throw new NotFoundException("Status ACTIVE product was not found");
        }
        log.info("Products was found");
        List<ProductsStockInfoResponse> productsStockInfoResponseList = new ArrayList<>();
        for (Product product : products) {
            ProductsStockInfoResponse productsStockInfoResponse = new ProductsStockInfoResponse();
            log.info("ProductStockInfoResponse was successfully created");
            productsStockInfoResponse.setProductId(product.getId());
            productsStockInfoResponse.setQuantity(product.getQuantity());
            productsStockInfoResponse.setProductName(product.getName());
            productsStockInfoResponseList.add(productsStockInfoResponse);
            log.info("ProductId , Quantity and ProductName was successfully set to ProductStockInfoResponse");
        }
        return productsStockInfoResponseList;
    }
}