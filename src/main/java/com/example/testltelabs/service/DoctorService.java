package com.example.testktelabs.service;

import com.example.testktelabs.entity.Doctor;

public interface DoctorService {

    Doctor getDoctorById(Integer id);
    void save(Doctor doctor);
    void update(Doctor doctor, Integer id);
}
