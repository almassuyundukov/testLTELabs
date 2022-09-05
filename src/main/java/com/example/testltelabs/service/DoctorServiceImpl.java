package com.example.testktelabs.service;

import com.example.testktelabs.entity.Doctor;
import com.example.testktelabs.exception.DoctorNotFoundException;
import com.example.testktelabs.repository.DoctorRepository;
import com.fasterxml.uuid.Generators;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService{

    private final DoctorRepository doctorRepository;

    @Override
    public Doctor getDoctorById(Integer id) {
        return doctorRepository.findById(id).orElseThrow(() ->
                new DoctorNotFoundException("Doctor with this " + id + " id not found"));
    }

    @Override
    public void save(Doctor doctor) {
        if(doctor.getUuid() == null){
            UUID generate = Generators.timeBasedGenerator().generate();
            doctor.setUuid(generate);
        }
        doctorRepository.save(doctor);
    }

    @Override
    public void update(Doctor doctor, Integer id) {
        Doctor doctorByDB = getDoctorById(id);
        doctorByDB.setFullName(doctor.getFullName());
        doctorRepository.save(doctorByDB);
    }

}
