package com.example.testktelabs.controller;

import com.example.testktelabs.entity.Doctor;
import com.example.testktelabs.entity.Patient;
import com.example.testktelabs.entity.Ticket;
import com.example.testktelabs.exception.IncorrectDateFormatException;
import com.example.testktelabs.service.DoctorService;
import com.example.testktelabs.service.PatientService;
import com.example.testktelabs.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final PatientService patientService;
    private final DoctorService doctorService;

    @GetMapping("/doctor={idDoctor}&date={dateStr}")
    public ResponseEntity<List<Ticket>> getTicketByDoctorAndDate(@PathVariable Integer idDoctor, @PathVariable String dateStr){
        Calendar date = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            date.setTime(sdf.parse(dateStr));
        } catch (ParseException e) {
            throw new IncorrectDateFormatException("Incorrect Date Format, please use yyyy-MM-dd");
        }
        return ResponseEntity.ok().body(ticketService.findTicketsByDoctorAndDate(idDoctor, date));
    }

    @PutMapping("/patient/{id}")
    public ResponseEntity<String> putPatientOnTicket(@PathVariable Integer id, @RequestBody Patient patient){
        Patient patientById = patientService.getPatientById(patient.getId());
        ticketService.setPatientOnTicket(patientById, id);
        return ResponseEntity.ok().body("Ok");
    }

    @PutMapping("/doctor/{id}")
    public ResponseEntity<String> putDoctorOnTicket(@PathVariable Integer id, @RequestBody Doctor doctor){
        Doctor doctorById = doctorService.getDoctorById(doctor.getId());
        ticketService.setDoctorOnTicket(doctorById, id);
        return ResponseEntity.ok().body("Ok");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTicket(@PathVariable Integer id){
        ticketService.delete(id);
        return ResponseEntity.ok().body("Ticket with id "+id+" is deleted");
    }

}
