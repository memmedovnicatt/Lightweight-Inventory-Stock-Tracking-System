package com.nicat.lightweightinventorystocktrackingsystem.model.dto.response;

import com.nicat.lightweightinventorystocktrackingsystem.model.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryUpdateResponse {
    String newName;

    @Enumerated(EnumType.STRING)
    Status newStatus;
}