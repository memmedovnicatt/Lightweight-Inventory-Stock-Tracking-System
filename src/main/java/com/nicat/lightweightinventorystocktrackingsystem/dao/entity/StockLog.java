package com.nicat.lightweightinventorystocktrackingsystem.dao.entity;

import com.nicat.lightweightinventorystocktrackingsystem.model.enums.ChangeType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_logs")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class StockLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDateTime logTime;
    String reason;

    @Enumerated(EnumType.STRING)
    ChangeType changeType;

    Integer changeQuantity;
    Integer previousQuantity;
    Integer newQuantity;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    Product product;
}