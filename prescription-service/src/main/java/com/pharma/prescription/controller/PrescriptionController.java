package com.pharma.prescription.controller;

import com.pharma.prescription.client.InventoryClient;
import com.pharma.prescription.model.Prescription;
import com.pharma.prescription.repository.PrescriptionRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor // Automatically creates a constructor for the final repository field
public class PrescriptionController {

    private final PrescriptionRepository repository;

    private final InventoryClient inventoryClient;

    // POST: http://localhost:8081/api/prescriptions
    /*@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prescription createPrescription(@RequestBody Prescription prescription) {
        return repository.save(prescription);
    }*/

    // GET: http://localhost:8081/api/prescriptions
    @GetMapping
    public List<Prescription> getAllPrescriptions() {
        return repository.findAll();
    }

    // GET by ID: http://localhost:8081/api/prescriptions/{id}
    @GetMapping("/{id}")
    public Prescription getById(@PathVariable String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found with id: " + id));
    }

    /*@PostMapping
// 'name' identifies the config, 'fallbackMethod' is the backup function
    @CircuitBreaker(name = "inventoryCheck", fallbackMethod = "handleInventoryFailure")
    public Prescription create(@RequestBody Prescription rx) {
        String medName = rx.getMedications().get(0).getName();
        int qty = rx.getMedications().get(0).getDurationDays();

        // If this call fails (Inventory Service is down), it triggers the fallback
        boolean isAvailable = inventoryClient.checkStock(medName, qty);

        if (isAvailable) {
            return repository.save(rx);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Out of stock");
        }
    }*/


    // KEEP ONLY THIS POST METHOD
    @PostMapping
    @CircuitBreaker(name = "inventoryCheck", fallbackMethod = "handleInventoryFailure")
    public Prescription createPrescription(@RequestBody Prescription rx) {
        String medName = rx.getMedications().get(0).getName();
        int qty = rx.getMedications().get(0).getDurationDays();

        boolean isAvailable = inventoryClient.checkStock(medName, qty);

        if (isAvailable) {
            return repository.save(rx);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Out of stock");
        }
    }

    // FALLBACK METHOD: Must have the same parameters + a Throwable
    public Prescription handleInventoryFailure(Prescription rx, Throwable t) {
        // In Pharma Ops, we log this as a critical system degradation
        System.out.println("ALERT: Inventory Service is unreachable. Triggering Fallback.");

        // Instead of a 500 error, we return a meaningful 503 for the UI
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                "Inventory validation is currently unavailable. Please try again in a few minutes.");
    }

}