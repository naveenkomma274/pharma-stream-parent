package com.pharma.prescription.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "prescriptions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prescription {
    @Id
    private String id;
    private String doctorId;
    private String patientId;
    private List<Medication> medications;
    private LocalDateTime createdAt = LocalDateTime.now();
}