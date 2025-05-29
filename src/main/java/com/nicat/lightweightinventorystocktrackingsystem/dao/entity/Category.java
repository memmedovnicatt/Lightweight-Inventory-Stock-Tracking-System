package com.nicat.lightweightinventorystocktrackingsystem.dao.entity;

import com.nicat.lightweightinventorystocktrackingsystem.model.enums.Status;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "categories")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    LocalDateTime createdAt;
    LocalDateTime updateAt;
    LocalDateTime deletedAt;
    @Enumerated(EnumType.STRING)
    Status status;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Product> products;
}