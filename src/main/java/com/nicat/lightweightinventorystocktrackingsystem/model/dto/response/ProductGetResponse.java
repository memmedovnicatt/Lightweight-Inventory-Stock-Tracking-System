package com.nicat.lightweightinventorystocktrackingsystem.model.dto.response;

import com.nicat.lightweightinventorystocktrackingsystem.dao.entity.Category;
import com.nicat.lightweightinventorystocktrackingsystem.model.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductGetResponse {
    String barcode;
    String generateBarcodeTimestamp;
    String categoryName;

    @Enumerated(EnumType.STRING)
    Status status;

    LocalDateTime createdAt;
    LocalDateTime updateAt;
    LocalDateTime deletedAt;
}