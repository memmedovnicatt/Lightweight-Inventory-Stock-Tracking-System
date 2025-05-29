package com.nicat.lightweightinventorystocktrackingsystem.controller;

import com.nicat.lightweightinventorystocktrackingsystem.model.dto.request.CategoryAddRequest;
import com.nicat.lightweightinventorystocktrackingsystem.model.dto.request.CategoryUpdateRequest;
import com.nicat.lightweightinventorystocktrackingsystem.model.dto.response.AllCategoriesResponse;
import com.nicat.lightweightinventorystocktrackingsystem.model.dto.response.CategoryAddResponse;
import com.nicat.lightweightinventorystocktrackingsystem.model.dto.response.CategoryUpdateResponse;
import com.nicat.lightweightinventorystocktrackingsystem.model.dto.response.ProductsStockInfoResponse;
import com.nicat.lightweightinventorystocktrackingsystem.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {

    CategoryService categoryService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public CategoryAddResponse add(@RequestBody CategoryAddRequest categoryAddRequest) {
        return categoryService.add(categoryAddRequest);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        categoryService.softDelete(id);
    }

    @PatchMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryUpdateResponse update(@PathVariable Long id,
                                         @RequestBody CategoryUpdateRequest categoryUpdateRequest) {
        return categoryService.update(id, categoryUpdateRequest);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<AllCategoriesResponse> getAll() {
        return categoryService.getAll();
    }


}