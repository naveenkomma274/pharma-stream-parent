package com.pharma.prescription.repository;

import com.pharma.prescription.model.Prescription;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends MongoRepository<Prescription, String> {
    // MongoRepository gives you save(), findById(), and findAll() for free!
}