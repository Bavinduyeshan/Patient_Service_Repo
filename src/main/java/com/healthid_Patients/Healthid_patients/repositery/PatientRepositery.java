package com.healthid_Patients.Healthid_patients.repositery;



import  com.healthid_Patients.Healthid_patients.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepositery extends JpaRepository<Patient,Integer> {
    Patient findByUserId(Integer userId);
}
