package com.nicat.lightweightinventorystocktrackingsystem.mapper;

import com.nicat.lightweightinventorystocktrackingsystem.dao.entity.Category;
import com.nicat.lightweightinventorystocktrackingsystem.model.dto.request.CategoryUpdateRequest;
import com.nicat.lightweightinventorystocktrackingsystem.model.dto.response.AllCategoriesResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper {
    @Mapping(source = "newName", target = "name")
    @Mapping(source = "newStatus", target = "status")
    void toUpdateCategory(
            CategoryUpdateRequest categoryUpdateRequest,
            @MappingTarget Category category); // for partial update without set null

    List<AllCategoriesResponse> toAllCategoriesResponse(List<Category> categoryList);
}