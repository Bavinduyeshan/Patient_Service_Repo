package com.healthid_Patients.Healthid_patients.controller;




import  com.healthid_Patients.Healthid_patients.model.Patient;
import  com.healthid_Patients.Healthid_patients.repositery.PatientRepositery;
import  com.healthid_Patients.Healthid_patients.service.Patientservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
// Adjust based on your frontend origin
@RestController
@RequestMapping("/pateints")
public class PatientController {

    @Autowired
    private Patientservice patientservice;

    private final PatientRepositery patientRepository;

    public PatientController(PatientRepositery patientRepository) {
        this.patientRepository = patientRepository;
    }
    @PostMapping("/add")
    public String addPatient(@RequestBody Patient patient) {
        patientservice .createPatient(patient);
        return "Patient added successfully!";
    }


    @GetMapping("/validate/{patientId}")
    public ResponseEntity<PatientResponse> validatePatient(@PathVariable Integer patientId) {
        Optional<Patient> patientOptional = patientservice.getPatientById(patientId);

        if (patientOptional.isEmpty()) {  // If patient does not exist, return 404
            return ResponseEntity.notFound().build();
        }

        Patient patient = patientOptional.get(); // Extract actual Patient object

        // Return only required patient details
        return ResponseEntity.ok(new PatientResponse(
                patient.getPatientId(),
                patient.getFirstname(),
                patient.getLastname(),
                patient.getEmail(),
                patient.getPhone(),
                patient.getDateOfBirth(),
                patient.getAddress(),
                patient.getGender()
        ));
    }

    // DTO class for response
    public static class PatientResponse {
        private Integer patientId;
        private String firstname;
        private String lastname;
        private String email;
        private String phone;

          private LocalDate dateOfBirth;

          private String address;

          private String gender;

        public PatientResponse(Integer patientId, String firstname, String lastname, String email, String phone,LocalDate dateOfBirth,String address,String gender) {
            this.patientId = patientId;
            this.firstname = firstname;
            this.lastname = lastname;
            this.email = email;
            this.phone = phone;
            this.dateOfBirth=dateOfBirth;
            this.address=address;
            this.gender=gender;
        }




        public Integer getPatientId() {
            return patientId;
        }

        public String getFirstname() {
            return firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }

        public LocalDate getDateOfBirth() {
            return dateOfBirth;
        }

        public String getAddress() {
            return address;
        }

        public String getGender() {
            return gender;
        }
    }
    @GetMapping("patients/getAll")
    public List<Patient> getAllPatients() {
        return patientservice.getAllPatients();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getPatientById(@PathVariable Integer id) {

        try {
            Optional<Patient> patient = patientservice.getPatientById(id);
            if (patient.isPresent()) {
                return  ResponseEntity.ok(patient.get());
            } else {
                return new ResponseEntity<>("patient not found",HttpStatus.OK);
            }
        }
        catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.OK);
        }
        // return patientservice.getPatientById(id);
    }

    @GetMapping("/patient/byUserId/{userId}")
    public ResponseEntity<PatientResponse> getPatientDataByUserId(@PathVariable Integer userId) {
        try {
            Patient patient = patientRepository.findByUserId(userId);
            if (patient != null) {
                PatientResponse response = new PatientResponse(
                        patient.getPatientId(),
                        patient.getFirstname(),
                        patient.getLastname(),
                        patient.getEmail(),
                        patient.getPhone(),
                        patient.getDateOfBirth(),
                        patient.getAddress(),
                        patient.getGender()
                );
                return ResponseEntity.ok(response);
            } else {
                return new ResponseEntity<>(new PatientResponse(null, null, null, null, null, null, null, null), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/patient/byUserId/{userId}")
//    public ResponseEntity<PatientResponse> getPatientDataByUserId(@PathVariable Integer userId) {
//        try {
//            Optional<Patient> optionalPatient = Optional.ofNullable(patientRepository.findByUserId(userId));
//
//            if (optionalPatient.isPresent()) {
//                Patient patient = optionalPatient.get();
//                PatientResponse patientResponse = new PatientResponse(
//                        patient.getPatientId(),
//                        patient.getFirstname(),
//                        patient.getLastname(),
//                        patient.getEmail(),
//                        patient.getPhone()
//                );
//                return ResponseEntity.ok(patientResponse);
//            } else {
//                return new ResponseEntity<>("Patient not found.", HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


    //    @PutMapping("/update/{id}")
//    public ResponseEntity<String> updatePatient(
//            @PathVariable Long id,
//            @RequestBody Patient patient) {
//        try {
//            String result = patientservice.updatePatient(id, patient);
//            return new ResponseEntity<>(result, HttpStatus.OK);
//        } catch (Exception ex) {
//            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
    @PutMapping("/update/{patientId}")
    public ResponseEntity<String> updatePatient(
            @PathVariable Integer patientId,
            @RequestBody Patient patient) {
        try {
            String result = patientservice.updatePatient(patientId, patient);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<String> deletePatient(@PathVariable Integer id) {
//
//        try {
//            Optional<Patient> existingpatient=patientservice.getPatientById(id);
//
//            if (existingpatient.isPresent()){
//                patientservice.deletePatient(id);
//                return ResponseEntity.ok("Succesfully deleted");
//            }
//            else{
//                return ResponseEntity.ok("no patient");
//            }
//        }
//        catch (Exception ex){
//            return ResponseEntity.ok(ex.getMessage());
//        }
//
//
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Boolean> validatePatientbyid(@PathVariable Integer id) {
        boolean exists = patientRepository.existsById(id);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/byUserId/{userId}")
    public ResponseEntity<PatientDTO> getPatientByUserId(@PathVariable Integer userId) {
        Patient patient = patientRepository.findByUserId(userId);
        if (patient != null) {
            return ResponseEntity.ok(new PatientDTO(patient.getPatientId(), patient.getUserId()));
        }
        return ResponseEntity.notFound().build();
    }

    class PatientDTO {
        private Integer patientId;
        private Integer userId;

        public PatientDTO(Integer patientId, Integer userId) {
            this.patientId=patientId;
            this.userId=userId;
        }
        // Constructor, getters


        public Integer getPatientId() {
            return patientId;
        }

        public void setPatientId(Integer patientId) {
            this.patientId = patientId;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }
    }

}
