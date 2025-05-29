package com.nicat.lightweightinventorystocktrackingsystem.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class BarcodeGenerateService {
    public String generateBarcode() {
        log.info("generateBarcode method was started");
        return "BC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public String generateBarcodeTimestamp() {
        log.info("generateBarcodeTimestamp method was started");
        return "BC-" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    }
}