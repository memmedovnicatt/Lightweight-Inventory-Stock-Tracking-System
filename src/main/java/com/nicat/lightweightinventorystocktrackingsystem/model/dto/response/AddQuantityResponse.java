package com.nicat.lightweightinventorystocktrackingsystem.model.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddQuantityResponse {
    Long productId;
    Integer quantity;
}