package com.nicat.lightweightinventorystocktrackingsystem.service;

import com.nicat.lightweightinventorystocktrackingsystem.dao.entity.Category;
import com.nicat.lightweightinventorystocktrackingsystem.dao.entity.Product;
import com.nicat.lightweightinventorystocktrackingsystem.dao.repository.CategoryRepository;
import com.nicat.lightweightinventorystocktrackingsystem.dao.repository.ProductRepository;
import com.nicat.lightweightinventorystocktrackingsystem.mapper.CategoryMapper;
import com.nicat.lightweightinventorystocktrackingsystem.model.dto.request.CategoryAddRequest;
import com.nicat.lightweightinventorystocktrackingsystem.model.dto.request.CategoryUpdateRequest;
import com.nicat.lightweightinventorystocktrackingsystem.model.dto.response.AllCategoriesResponse;
import com.nicat.lightweightinventorystocktrackingsystem.model.dto.response.CategoryAddResponse;
import com.nicat.lightweightinventorystocktrackingsystem.model.dto.response.CategoryUpdateResponse;
import com.nicat.lightweightinventorystocktrackingsystem.model.dto.response.ProductsStockInfoResponse;
import com.nicat.lightweightinventorystocktrackingsystem.model.enums.Status;
import com.nicat.lightweightinventorystocktrackingsystem.model.exception.child.AlreadyDeletedException;
import com.nicat.lightweightinventorystocktrackingsystem.model.exception.child.AlreadyExistName;
import com.nicat.lightweightinventorystocktrackingsystem.model.exception.child.NotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;
    ProductRepository productRepository;

    public CategoryAddResponse add(CategoryAddRequest categoryAddRequest) {
        log.info("add method was started");
        boolean existByName = categoryRepository.existsByName(categoryAddRequest.getName());
        if (existByName) {
            throw new AlreadyExistName("This name already exist in database");
        }
        Category category = new Category();
        category.setName(categoryAddRequest.getName());
        category.setCreatedAt(LocalDateTime.now());
        category.setStatus(Status.ACTIVE);
        log.info("categoryRequest successfully set to category");
        categoryRepository.save(category);
        log.info("category successfully saved in database");
        CategoryAddResponse categoryAddResponse = new CategoryAddResponse();
        categoryAddResponse.setId(category.getId());
        log.info("id successfully set to categoryResponse");
        return categoryAddResponse;
    }

    public void softDelete(Long id) {
        log.info("delete method was deleted");
        Category existsId = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category id: " + id + " is not found"));
        log.info("Category id {}  found", id);

        if (existsId.getStatus() == Status.DELETED) {
            throw new AlreadyDeletedException("Category already deleted");
        }
        existsId.setStatus(Status.DELETED);
        existsId.setDeletedAt(LocalDateTime.now());
        log.info("Category was deleted");
        categoryRepository.save(existsId); // for new local date
    }

    @Transactional
    public CategoryUpdateResponse update(Long id, CategoryUpdateRequest categoryUpdateRequest) {
        log.info("update method was started");
        log.info("category update request {}", categoryUpdateRequest);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category id " + id + "was not found"));
        log.info("id was found");
        categoryMapper.toUpdateCategory(categoryUpdateRequest, category);
        category.setUpdateAt(LocalDateTime.now());
        categoryRepository.save(category);
        CategoryUpdateResponse categoryUpdateResponse = new CategoryUpdateResponse();
        categoryUpdateResponse.setNewName(category.getName());
        categoryUpdateResponse.setNewStatus(category.getStatus());
        return categoryUpdateResponse;
    }

    public List<AllCategoriesResponse> getAll() {
        log.info("getAll method was started for categories controller");
        List<Category> categoriesResponseList = categoryRepository.findAll();
        if (categoriesResponseList.isEmpty()) {
            throw new NotFoundException("Categories are empty");
        }
        return categoryMapper.toAllCategoriesResponse(categoriesResponseList);
    }
}