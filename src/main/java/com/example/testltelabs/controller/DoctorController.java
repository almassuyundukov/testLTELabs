package com.example.testktelabs.controller;

import com.example.testktelabs.entity.Doctor;
import com.example.testktelabs.exception.DatabaseNotNULLException;
import com.example.testktelabs.exception.DatabaseUniqueValueException;
import com.example.testktelabs.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping("/")
    public ResponseEntity<String> createDoctor(@RequestBody Doctor doctor){
        try {
            doctorService.save(doctor);
        } catch (RuntimeException e) {
            // Распаковываем PSQLException с помощью библиотеки Guava
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    throw new DatabaseUniqueValueException("Doctor with this full name is exist");
                } if ("23502".equals(((SQLException) rootCause).getSQLState())){
                    throw new DatabaseNotNULLException("значение NULL в столбце full_name отношения doctor нарушает ограничение NOT NULL");
                }
            }
        }
        return ResponseEntity.ok().body("Doctor is created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDoctor(@PathVariable Integer id, @RequestBody Doctor doctor){
        try {
            doctorService.update(doctor, id);
        } catch (RuntimeException e) {
            // Распаковываем PSQLException с помощью библиотеки Guava
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    throw new DatabaseUniqueValueException("Doctor with this full name is exist");
                } if ("23502".equals(((SQLException) rootCause).getSQLState())){
                    throw new DatabaseNotNULLException("значение NULL в столбце full_name отношения doctor нарушает ограничение NOT NULL");
                }
            }
        }
        return ResponseEntity.ok().body("Doctor is update");
    }
}
