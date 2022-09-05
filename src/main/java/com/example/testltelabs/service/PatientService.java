package com.example.testktelabs.service;

import com.example.testktelabs.entity.Patient;

public interface PatientService {
    Patient getPatientById(Integer id);
    void save(Patient patient);
    void update(Patient patient, Integer id);;
}
