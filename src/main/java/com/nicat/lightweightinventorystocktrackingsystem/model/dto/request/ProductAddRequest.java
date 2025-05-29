package com.nicat.lightweightinventorystocktrackingsystem.model.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductAddRequest {
    String name;
    Double price;
    String currency;
    String categoryName;
}