package com.nicat.lightweightinventorystocktrackingsystem.dao.repository;

import com.nicat.lightweightinventorystocktrackingsystem.dao.entity.Category;
import com.nicat.lightweightinventorystocktrackingsystem.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c FROM Category c WHERE c.name = :name AND c.status = :status")
    Optional<Category> findByNameAndStatus(@Param("name") String name,
                                           @Param("status") Status status);

    boolean existsByName(String name);
}