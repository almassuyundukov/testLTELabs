package com.example.testktelabs.service;

import com.example.testktelabs.entity.Patient;
import com.example.testktelabs.exception.PatientNotFoundException;
import com.example.testktelabs.repository.PatientRepository;
import com.fasterxml.uuid.Generators;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService{

    private final PatientRepository patientRepository;

    @Override
    public Patient getPatientById(Integer id) {
        return patientRepository.findById(id).orElseThrow(() ->
                new PatientNotFoundException("Patient with this " +id+ " id not found"));
    }

    @Override
    public void save(Patient patient) {
        if(patient.getUuid() == null){
            UUID generate = Generators.timeBasedGenerator().generate();
            patient.setUuid(generate);
        }
        patientRepository.save(patient);
    }

    @Override
    public void update(Patient patient, Integer id) {
        UUID uuid = Generators.timeBasedGenerator().generate();
        Patient patientByDB = getPatientById(id);
        patientByDB.setUuid(uuid)
                .setFullName(patient.getFullName())
                .setDateOfBirth(patient.getDateOfBirth());
        patientRepository.save(patientByDB);
    }

}
