package com.nicat.lightweightinventorystocktrackingsystem.dao.repository;

import com.nicat.lightweightinventorystocktrackingsystem.dao.entity.StockLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockManagementRepository extends JpaRepository<StockLog, Long> {
    @Query(value = "select * from stock_logs where product_id= :productId order by log_time desc limit 1", nativeQuery = true)
    Optional<StockLog> findLatestLogTimeByProductId(@Param("productId") Long productId); // for to pull latest information
}