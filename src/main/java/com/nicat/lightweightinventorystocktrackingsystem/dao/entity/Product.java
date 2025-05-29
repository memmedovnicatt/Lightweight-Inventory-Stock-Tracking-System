package com.nicat.lightweightinventorystocktrackingsystem.dao.entity;

import com.nicat.lightweightinventorystocktrackingsystem.model.enums.Status;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    Double price;
    String currency;
    Integer quantity; // for stock
    String barcode; // for stock keeping unit
    String generateBarcodeTimestamp;
    LocalDateTime createdAt;
    LocalDateTime updateAt;
    LocalDateTime deletedAt;

    @Enumerated(EnumType.STRING)
    Status status;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<StockLog> stockLogs;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    Category category;
}