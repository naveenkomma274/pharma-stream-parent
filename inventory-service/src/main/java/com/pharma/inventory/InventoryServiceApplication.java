package com.pharma.inventory;

import com.pharma.inventory.model.Inventory;
import com.pharma.inventory.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient // This makes the service visible to Eureka
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    // This runs automatically on startup to put data in the H2 Database
    @Bean
    public CommandLineRunner loadData(InventoryRepository repository) {
        return args -> {
            repository.save(new Inventory(null, "Paracetamol", 100));
            repository.save(new Inventory(null, "Amoxicillin", 50));
            System.out.println(">>> H2 Inventory Data Seeded Successfully! <<<");
        };
    }
}