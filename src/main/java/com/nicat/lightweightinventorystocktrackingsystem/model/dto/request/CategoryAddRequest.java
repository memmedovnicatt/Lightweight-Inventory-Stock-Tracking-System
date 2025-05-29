package com.nicat.lightweightinventorystocktrackingsystem.model.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryAddRequest {
    String name;
}
