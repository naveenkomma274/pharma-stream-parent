package com.pharma.prescription.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// The 'name' must match the 'spring.application.name' of your inventory-service
@FeignClient(name = "inventory-service")
public interface InventoryClient {
    // Ensure this path matches the COMBINED class + method path of the controller
    @GetMapping("/api/inventory/check")
    boolean checkStock(@RequestParam("name") String name, @RequestParam("requestedQty") int requestedQty);
}