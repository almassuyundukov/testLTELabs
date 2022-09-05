package com.example.testktelabs.repository;

import com.example.testktelabs.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Calendar;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findTicketsByStartTimeAdmissionBetweenAndDoctor_IdAndPatientIsNull(Calendar startDate, Calendar endDate, Integer doctor_id);
    List<Ticket> findTicketsByPatientId(Integer id);
}
