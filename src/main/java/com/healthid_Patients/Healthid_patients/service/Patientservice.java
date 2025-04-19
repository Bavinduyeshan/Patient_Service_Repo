package com.healthid_Patients.Healthid_patients.service;




import  com.healthid_Patients.Healthid_patients.model.Patient;

import  com.healthid_Patients.Healthid_patients.repositery.PatientRepositery;
import  com.healthid_Patients.Healthid_patients.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
public class Patientservice {

    @Autowired
    private PatientRepositery patientRepository;


    @Autowired
    private WebClient.Builder webClientBuilder;

    private final String USER_SERVICE_URL = "http://user-service-url";

//
//    @Transactional
//    public String createPatient(Patient patient) {
//        patientRepository.save(patient);
//        qrCodeService.generateqrcode(patient.getPatientId(),"qrcodes/"+patient.getPatientId()+".PNG");
//        return "Patient added and qr code generated succesfully";
//    }

//    public String createPatient(Patient patient) {
//        // Save the patient to the database
//        Patient savedPatient = patientRepository.save(patient);
//
//        // Generate a QR code for the saved patient
//        try {
//            qrCodeService.generateqrcode(
//                    Long.valueOf(String.valueOf(savedPatient.getPatientId())), // Content for the QR code
//                    "qrcodes/" + savedPatient.getPatientId() + ".PNG" // Path to save the QR code
//            );
//            return "Patient added and QR code generated successfully.";
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to generate QR code: " + e.getMessage());
//        }
//    }


    @Autowired
    private UserServiceClient userServiceClient;

    // private final String USER_SERVICE_URL = "http://user-service-url";

//    public Patient createPatient(Patient patient) {
//        if (patient.getUser() == null || patient.getUser().getUserId() == 0) {
//            throw new RuntimeException("User information is missing in Patient request");
//        }
//
//        // Call Users service
//        UserResponse user = userServiceClient.validateUser(patient.getUser().getUserId());
//
//        if (user == null || !user.getRole().equalsIgnoreCase("PATIENT")) {
//            throw new RuntimeException("Invalid user. Only patients can be registered.");
//        }
//
//        return patientRepository.save(patient);
//    }

    public Patient createPatient(Patient patient) {
        if (patient.getUserId() == null) {
            throw new RuntimeException("User ID is missing in Patient request");
        }

        // Call Users service to validate user
        UserResponse user = userServiceClient.validateUser(patient.getUserId());

        if (user == null || !user.getRole().equalsIgnoreCase("Patient")) {
            throw new RuntimeException("Invalid user. Only patients can be registered.");
        }

        return patientRepository.save(patient);
    }



//    public Patient createPatient(Patient patient) {
//        // Fetch user details from User service
//        User user = fetchUserById(patient.getUserId());
//
//        if (user != null) {
//            patient.setEmail(user.getEmail());  // Set user email or other user details
//            return patientRepository.save(patient);
//        } else {
//            throw new RuntimeException("User not found!");
//        }
//    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(Integer patientId) {
        return patientRepository.findById(patientId);
    }

    //    public String updatePatient(Long id, Patient patient) {
//        Optional<Patient> existingPatient = patientRepository.findById(id);
//        if (existingPatient.isPresent()) {
//            Patient updatedPatient = existingPatient.get();
//
//            // Update fields
//            updatedPatient.setName(patient.getName());
//            updatedPatient.setEmail(patient.getEmail());
//            updatedPatient.setPhone(patient.getPhone());
//            updatedPatient.setAddress(patient.getAddress());
//            updatedPatient.setDateOfBirth(patient.getDateOfBirth());
//            updatedPatient.setQrCode(patient.getQrCode());
//
//            // Save updated patient
//            patientRepository.save(updatedPatient);
//            return "Patient updated successfully!";
//        } else {
//            throw new RuntimeException("Patient not found!");
//        }
//    }
    public String updatePatient(Integer id, Patient patient) {
        Optional<Patient> existingPatient = patientRepository.findById(id);
        if (existingPatient.isPresent()) {
            Patient updatedPatient = existingPatient.get();

            updatedPatient.setFirstname(patient.getFirstname());
            updatedPatient.setLastname(patient.getLastname());
            updatedPatient.setEmail(patient.getEmail());
            updatedPatient.setPhone(patient.getPhone());
            updatedPatient.setAddress(patient.getAddress());
            updatedPatient.setDateOfBirth(patient.getDateOfBirth());
            updatedPatient.setGender(patient.getGender());
//            updatedPatient.setUserId(patient.getUserId());
            updatedPatient.setLastModifiedDate(java.time.LocalDateTime.now());
            updatedPatient.setLastModifiedBy(patient.getLastModifiedBy());

            // Save updated patient
            patientRepository.save(updatedPatient);
            return "Patient updated successfully!";
        } else {
            throw new RuntimeException("Patient not found!");
        }
    }

    public void deletePatient(Integer patientId) {
        patientRepository.deleteById(patientId);
    }


    public  long  patinetCount(){
      return  patientRepository.count();
    }
}
