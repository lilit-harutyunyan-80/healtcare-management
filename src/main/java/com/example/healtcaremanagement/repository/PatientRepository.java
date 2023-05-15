package com.example.healtcaremanagement.repository;


import com.example.healtcaremanagement.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
}
