package com.nicat.lightweightinventorystocktrackingsystem.service;

import com.nicat.lightweightinventorystocktrackingsystem.dao.entity.Product;
import com.nicat.lightweightinventorystocktrackingsystem.dao.entity.StockLog;
import com.nicat.lightweightinventorystocktrackingsystem.dao.repository.ProductRepository;
import com.nicat.lightweightinventorystocktrackingsystem.dao.repository.StockManagementRepository;
import com.nicat.lightweightinventorystocktrackingsystem.model.dto.request.AddQuantityRequest;
import com.nicat.lightweightinventorystocktrackingsystem.model.dto.request.DeleteQuantityRequest;
import com.nicat.lightweightinventorystocktrackingsystem.model.dto.response.AddQuantityResponse;
import com.nicat.lightweightinventorystocktrackingsystem.model.dto.response.DeleteQuantityResponse;
import com.nicat.lightweightinventorystocktrackingsystem.model.enums.ChangeType;
import com.nicat.lightweightinventorystocktrackingsystem.model.exception.child.CanNotDeletedException;
import com.nicat.lightweightinventorystocktrackingsystem.model.exception.child.NotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StockService {

    ProductRepository productRepository;
    StockManagementRepository stockManagementRepository;

    @Transactional
    public AddQuantityResponse add(Long productId, AddQuantityRequest addQuantityRequest) {
        log.info("add method was started for Stock Service");
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("This product was not found"));
        log.info("Product was found");
        Integer currentQuantity = product.getQuantity();
        if (currentQuantity == null) {
            currentQuantity = 0;
            currentQuantity = currentQuantity + addQuantityRequest.getQuantity();
        } else {
            currentQuantity = currentQuantity + addQuantityRequest.getQuantity();
        }
        product.setQuantity(currentQuantity);
        productRepository.save(product);
        log.info("Product successfully saved to database");

        StockLog latestLog = stockManagementRepository.findLatestLogTimeByProductId(productId)
                .orElse(null);
        StockLog stockLog = new StockLog();
        log.info("StockLog created");
        stockLog.setReason(addQuantityRequest.getReason());
        stockLog.setProduct(product);
        stockLog.setChangeType(ChangeType.INCREASE);
        stockLog.setLogTime(LocalDateTime.now());

        Integer changeQuantity = addQuantityRequest.getQuantity();
        stockLog.setChangeQuantity(changeQuantity);

        if (latestLog == null) {
            stockLog.setPreviousQuantity(0);
            stockLog.setNewQuantity(changeQuantity);
        } else {
            Integer prevQuantity = latestLog.getNewQuantity();
            Integer newQuantity = prevQuantity + changeQuantity;
            stockLog.setPreviousQuantity(prevQuantity);
            stockLog.setNewQuantity(newQuantity);
        }
        stockManagementRepository.save(stockLog);
        log.info("StockLog successfully saved in database");
        AddQuantityResponse addQuantityResponse = new AddQuantityResponse();
        log.info("AddQuantityResponse was created");
        addQuantityResponse.setQuantity(product.getQuantity());
        addQuantityResponse.setProductId(product.getId());
        log.info("Quantity and ProductId successfully set to AddQuantityResponse");
        return addQuantityResponse;
    }

    public DeleteQuantityResponse delete(Long productId, DeleteQuantityRequest deleteQuantityRequest) {
        log.info("delete method was started for stockService");
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("This product was not found"));
        log.info("product was found");
        if (product.getQuantity() < 0 || product.getQuantity() < deleteQuantityRequest.getQuantity()) {
            throw new CanNotDeletedException("This quantity can not deleted from this product");
        }
        Integer currentQuantity = product.getQuantity();
        if (currentQuantity == 0) {
            throw new CanNotDeletedException("Quantity equals to zero ,can not deleted");
        }
        Integer newQuantityForProduct = currentQuantity - deleteQuantityRequest.getQuantity();
        product.setQuantity(newQuantityForProduct);
        productRepository.save(product);
        log.info("Product was successfully saved in database");

        StockLog stockLog = new StockLog();
        log.info("StockLog created was successfully");
        stockLog.setReason(deleteQuantityRequest.getReason());
        stockLog.setProduct(product);
        stockLog.setChangeType(ChangeType.DECREASE);
        stockLog.setLogTime(LocalDateTime.now());
        Integer changeQuantity = deleteQuantityRequest.getQuantity();
        Integer newQuantityForStock = product.getQuantity();
        Integer previousQuantity = changeQuantity + newQuantityForStock;
        stockLog.setChangeQuantity(changeQuantity);
        stockLog.setNewQuantity(newQuantityForStock);
        stockLog.setPreviousQuantity(previousQuantity);
        stockManagementRepository.save(stockLog);
        log.info("StockLog was successfully saved in database");

        DeleteQuantityResponse deleteQuantityResponse = new DeleteQuantityResponse();
        log.info("DeleteQuantityResponse was created");
        deleteQuantityResponse.setProductId(productId);
        deleteQuantityResponse.setQuantity(product.getQuantity());
        log.info("Quantity and ProductIDd was successfully set to DeleteQuantityResponse");
        return deleteQuantityResponse;
    }
}