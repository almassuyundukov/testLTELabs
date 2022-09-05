package com.example.testktelabs.controller;

import com.example.testktelabs.entity.Patient;
import com.example.testktelabs.entity.Ticket;
import com.example.testktelabs.exception.DatabaseNotNULLException;
import com.example.testktelabs.exception.DatabaseUniqueValueException;
import com.example.testktelabs.service.PatientService;
import com.example.testktelabs.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final TicketService ticketService;

    @GetMapping("/{id}")
    public ResponseEntity<List<Ticket>> getAllTicketByPatient(@PathVariable Integer id){
        patientService.getPatientById(id);
        List<Ticket> ticketsByPatient = ticketService.findTicketsByPatient(id);
        return ResponseEntity.ok().body(ticketsByPatient);
    }

    @PostMapping("/")
    public ResponseEntity<String> createPatient(@RequestBody Patient patient){
        try {
            patientService.save(patient);
        } catch (RuntimeException e) {
            // Распаковываем PSQLException с помощью библиотеки Guava
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    throw new DatabaseUniqueValueException("Patient with this full name is exist");
                } if ("23502".equals(((SQLException) rootCause).getSQLState())){
                    throw new DatabaseNotNULLException("значение NULL в столбце full_name отношения patient нарушает ограничение NOT NULL");
                }
            }
        }
        return ResponseEntity.ok().body("Patient is create");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePatient(@PathVariable Integer id, @RequestBody Patient patient){
        try {
            patientService.update(patient, id);
        } catch (RuntimeException e) {
            // Распаковываем PSQLException с помощью библиотеки Guava
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    throw new DatabaseUniqueValueException("Patient with this full name is exist");
                } if ("23502".equals(((SQLException) rootCause).getSQLState())){
                    throw new DatabaseNotNULLException("значение NULL в столбце full_name отношения patient нарушает ограничение NOT NULL");
                }
            }
        }
        return ResponseEntity.ok().body("Patient is update");
    }


}
