package com.nicat.lightweightinventorystocktrackingsystem.mapper;

import com.nicat.lightweightinventorystocktrackingsystem.dao.entity.Product;
import com.nicat.lightweightinventorystocktrackingsystem.model.dto.request.ProductUpdateRequest;
import com.nicat.lightweightinventorystocktrackingsystem.model.dto.response.AllProductsByCategoryResponse;
import com.nicat.lightweightinventorystocktrackingsystem.model.dto.response.AllProductsResponse;
import com.nicat.lightweightinventorystocktrackingsystem.model.dto.response.ProductGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {
    @Mapping(source = "newCurrency", target = "currency")
    @Mapping(source = "newName", target = "name")
    @Mapping(source = "newPrice", target = "price")
    @Mapping(source = "newStatus", target = "status")
    void toUpdateProduct(ProductUpdateRequest productUpdateRequest, @MappingTarget Product product); // for partial update without set null

    @Mapping(source = "category.name", target = "categoryName")
    ProductGetResponse toProductGetResponse(Product product);

    AllProductsResponse toAllProductsResponse(Product productPage);
}