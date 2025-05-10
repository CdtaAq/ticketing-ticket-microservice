
package com.example.ticketinggateway.ticket;

import com.example.ticketinggateway.ticket.entity.Ticket;
import com.example.ticketinggateway.ticket.repository.TicketRepository;
import com.example.ticketinggateway.ticket.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TicketServiceTest {

    @Autowired
    private TicketService ticketService;

    @MockBean
    private TicketRepository ticketRepository;

    private Ticket sampleTicket;

    @BeforeEach
    void setUp() {
        sampleTicket = new Ticket();
        sampleTicket.setId(1L);
        sampleTicket.setTitle("Sample Ticket");
        sampleTicket.setStatus("OPEN");
    }

    @Test
    void testCreateTicket() {
        when(ticketRepository.save(Mockito.any(Ticket.class))).thenReturn(sampleTicket);
        Ticket createdTicket = ticketService.createTicket(sampleTicket);
        assertNotNull(createdTicket);
        assertEquals("Sample Ticket", createdTicket.getTitle());
        verify(ticketRepository, times(1)).save(sampleTicket);
    }
}
