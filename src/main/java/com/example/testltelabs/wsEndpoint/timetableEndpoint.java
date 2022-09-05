package com.example.testktelabs.wsEndpoint;

import com.example.testktelabs.entity.Ticket;
import com.example.testktelabs.service.TicketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


import service.testlte_labs.ws.sample.CreateTimetableRequest;
import service.testlte_labs.ws.sample.CreateTimetableResponse;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.util.Calendar;


@Endpoint
public class timetableEndpoint {

    private static final String NAMESPACE_URI = "http://service/testLTE-Labs/ws/sample";

    private final TicketServiceImpl ticketService;

    @Autowired
    public timetableEndpoint(TicketServiceImpl ticketService){
        this.ticketService = ticketService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createTimetableRequest")
    @ResponsePayload
    public CreateTimetableResponse createTimetable(@RequestPayload CreateTimetableRequest request){
        CreateTimetableResponse response = new CreateTimetableResponse();
        Duration period = request.getPeriod();
        XMLGregorianCalendar day = request.getDay();
        BigInteger count = request.getCount();
        Calendar calendar = day.toGregorianCalendar();
        Duration startWorkDay = request.getStartWorkDay();
//        Установление времени на начало рабочего дня
        calendar.add(Calendar.SECOND, startWorkDay.getSeconds());
        calendar.add(Calendar.MINUTE, startWorkDay.getMinutes());
        calendar.add(Calendar.HOUR, startWorkDay.getHours());
        for (int i = 0; i < count.intValue() ; i++){
                Ticket ticket = new Ticket().setStartTimeAdmission(calendar);
                ticketService.save(ticket);
//                Добавление количества времени в соответствии с установленным периодом
                calendar.add(Calendar.SECOND, period.getSeconds());
                calendar.add(Calendar.MINUTE, period.getMinutes());
                calendar.add(Calendar.HOUR, period.getHours());
        }
        response.setCount(request.getCount());
        return response;
    }
}
