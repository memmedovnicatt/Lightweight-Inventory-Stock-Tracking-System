package com.nicat.lightweightinventorystocktrackingsystem.dao.repository;

import com.nicat.lightweightinventorystocktrackingsystem.dao.entity.Product;
import com.nicat.lightweightinventorystocktrackingsystem.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByStatus(Pageable pageable, Status status);

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findAllByStatus(Status status);
}