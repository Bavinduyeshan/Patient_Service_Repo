package com.healthid_Patients.Healthid_patients.model;

import com.healthid_Patients.Healthid_patients.model.Patient;
import jakarta.persistence.*;

import java.util.List;

@Entity

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "userId")
    private List<Patient> patients; // Optional: If users can have multiple patients


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
