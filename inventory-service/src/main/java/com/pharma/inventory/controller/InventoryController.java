package com.pharma.inventory.controller;

import com.pharma.inventory.model.Inventory;
import com.pharma.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryRepository repository;

    @GetMapping("/check")
    public boolean isInStock(@RequestParam("name") String name, @RequestParam("requestedQty") int requestedQty) {
        return repository.findByMedicineName(name)
                .map(inv -> inv.getQuantity() >= requestedQty)
                .orElse(false);
    }

    // 1. GET ALL: http://localhost:8082/api/inventory
    @GetMapping
    public List<Inventory> getAllInventory() {
        return repository.findAll();
    }

    // 2. POST: http://localhost:8082/api/inventory
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Inventory addStock(@RequestBody Inventory inventory) {
        return repository.save(inventory);
    }
}
