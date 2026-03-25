package com.anupam1897.repository;

import com.anupam1897.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findByTicketNo(String ticketNo);
    List<Ticket> findByPassengerId(Long passengerId);
    List<Ticket> findByTrainId(Long trainId);
    List<Ticket> findByBookedByClerkId(Long clerkId);
    List<Ticket> findByPassengerIdAndStatus(Long passengerId, Ticket.TicketStatus status);
}