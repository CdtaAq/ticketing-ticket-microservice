package com.example.ticketing.service;

import com.example.ticketing.entity.Ticket;
import com.example.ticketing.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketCronService {

    @Autowired
    private TicketRepository ticketRepo;

    @Autowired
    private JmsTemplate jmsTemplate;

    // Check tickets pending >7 days and notify manager
    @Scheduled(cron = "0 0 10 * * ?") // every day at 10AM
    public void notifyManagerAboutOldTickets() {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(7);
        List<Ticket> oldTickets = ticketRepo.findOldPendingTickets(cutoff);

        oldTickets.forEach(ticket -> {
            jmsTemplate.convertAndSend("ticket.notification", "Ticket ID " + ticket.getId() + " pending > 7 days.");
        });
    }

    // Auto-close resolved tickets older than 5 days
    @Scheduled(cron = "0 0 2 * * ?") // every day at 2AM
    public void autoCloseResolvedTickets() {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(5);
        List<Ticket> tickets = ticketRepo.findResolvedOlderThan(cutoff);
        tickets.forEach(ticket -> {
            ticket.setStatus("CLOSED");
            ticketRepo.save(ticket);
        });
    }
}
