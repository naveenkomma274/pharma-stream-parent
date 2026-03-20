package com.pharma.inventory.controller;

import com.pharma.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryRepository repository;

    @GetMapping("/check")
    public boolean isInStock(@RequestParam String name, @RequestParam int requestedQty) {
        return repository.findByMedicineName(name)
                .map(inv -> inv.getQuantity() >= requestedQty)
                .orElse(false);
    }
}
