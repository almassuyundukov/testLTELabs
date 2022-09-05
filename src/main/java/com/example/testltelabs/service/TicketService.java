package com.example.testktelabs.service;

import com.example.testktelabs.entity.Doctor;
import com.example.testktelabs.entity.Patient;
import com.example.testktelabs.entity.Ticket;

import java.util.Calendar;
import java.util.List;

public interface TicketService {

    Ticket findTicketById(Integer id);
    void save(Ticket ticket);
    void setDoctorOnTicket(Doctor doctor, Integer id);
    void setPatientOnTicket(Patient patient, Integer id);
    void delete(Integer id);
    List<Ticket> findTicketsByDoctorAndDate(Integer idDoctor, Calendar date);
    List<Ticket> findTicketsByPatient(Integer idPatient);
}
