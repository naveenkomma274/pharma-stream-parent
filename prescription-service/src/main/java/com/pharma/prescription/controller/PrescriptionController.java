package com.pharma.prescription.controller;

import com.pharma.prescription.model.Prescription;
import com.pharma.prescription.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor // Automatically creates a constructor for the final repository field
public class PrescriptionController {

    private final PrescriptionRepository repository;

    // POST: http://localhost:8081/api/prescriptions
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prescription createPrescription(@RequestBody Prescription prescription) {
        return repository.save(prescription);
    }

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
}