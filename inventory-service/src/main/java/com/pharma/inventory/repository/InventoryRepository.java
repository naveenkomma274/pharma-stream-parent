package com.pharma.inventory.repository;

import com.pharma.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    // You MUST add this line. Spring parses the name "findBy" + "MedicineName"
    Optional<Inventory> findByMedicineName(String medicineName);
}