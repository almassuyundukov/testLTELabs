package com.example.testktelabs.service;

import com.example.testktelabs.entity.Doctor;
import com.example.testktelabs.entity.Patient;
import com.example.testktelabs.entity.Ticket;
import com.example.testktelabs.exception.TicketNotFoundException;
import com.example.testktelabs.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Override
    public Ticket findTicketById(Integer id) {
        return ticketRepository.findById(id).orElseThrow(() ->
                new TicketNotFoundException("Ticket with this " + id + " id not found"));
    }

    @Override
    public void save(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    @Override
    public void setDoctorOnTicket(Doctor doctor, Integer id) {
        Ticket ticket = findTicketById(id);
        ticket.setDoctor(doctor);
        ticketRepository.save(ticket);
    }

    @Override
    public void setPatientOnTicket(Patient patient, Integer id) {
        Ticket ticket = findTicketById(id);
        ticket.setPatient(patient);
        ticketRepository.save(ticket);
    }

    @Override
    public void delete(Integer id) {
        Ticket ticketByDB = findTicketById(id);
        ticketRepository.delete(ticketByDB);
    }

    @Override
    public List<Ticket> findTicketsByDoctorAndDate(Integer idDoctor, Calendar date) {
        Calendar endDate = new GregorianCalendar();
        endDate.setTime(date.getTime());
        endDate.add(Calendar.DAY_OF_WEEK, 1);
        endDate.add(Calendar.SECOND, -1);
        return ticketRepository.findTicketsByStartTimeAdmissionBetweenAndDoctor_IdAndPatientIsNull(date, endDate, idDoctor);
    }

    @Override
    public List<Ticket> findTicketsByPatient(Integer idPatient) {
        return ticketRepository.findTicketsByPatientId(idPatient);
    }
}
