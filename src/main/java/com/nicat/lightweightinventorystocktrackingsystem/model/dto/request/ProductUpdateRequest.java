package com.nicat.lightweightinventorystocktrackingsystem.model.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductUpdateRequest {
    String newCurrency;
    String newName;
    Double newPrice;
    String newCategoryName;

    @Enumerated(EnumType.STRING)
    String newStatus;
}