package com.nicat.lightweightinventorystocktrackingsystem.controller;

import com.nicat.lightweightinventorystocktrackingsystem.model.dto.request.AddQuantityRequest;
import com.nicat.lightweightinventorystocktrackingsystem.model.dto.request.DeleteQuantityRequest;
import com.nicat.lightweightinventorystocktrackingsystem.model.dto.response.AddQuantityResponse;
import com.nicat.lightweightinventorystocktrackingsystem.model.dto.response.DeleteQuantityResponse;
import com.nicat.lightweightinventorystocktrackingsystem.service.StockService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product-stocks")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StockManagementController {
    StockService stockService;

    @PostMapping("/add/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public AddQuantityResponse add(@PathVariable Long productId,
                                   @RequestBody AddQuantityRequest addQuantityRequest) {
        return stockService.add(productId, addQuantityRequest);
    }

    @PostMapping("/delete/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public DeleteQuantityResponse delete(@PathVariable Long productId,
                                         @RequestBody DeleteQuantityRequest deleteQuantityRequest) {
        return stockService.delete(productId, deleteQuantityRequest);
    }
}