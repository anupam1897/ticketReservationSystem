package com.anupam1897.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ticket_clerks")
public class TicketClerk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String employeeId;

    private String station;
    private String shift;    // MORNING, AFTERNOON, NIGHT

    @JsonIgnore
    @OneToMany(mappedBy = "bookedByClerk", cascade = CascadeType.ALL)
    private List<Ticket> ticketsProcessed = new ArrayList<>();

    public TicketClerk() {}

    public TicketClerk(String name, String employeeId,
                       String station, String shift) {
        this.name       = name;
        this.employeeId = employeeId;
        this.station    = station;
        this.shift      = shift;
    }

    // ---- Business Methods ----
    public int getTotalTicketsProcessed() {
        return ticketsProcessed.size();
    }

    public long getActiveBookings() {
        return ticketsProcessed.stream()
            .filter(t -> !t.isCancelled())
            .count();
    }

    public long getCancelledBookings() {
        return ticketsProcessed.stream()
            .filter(Ticket::isCancelled)
            .count();
    }

    // ---- Getters & Setters ----
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getStation() { return station; }
    public void setStation(String station) { this.station = station; }

    public String getShift() { return shift; }
    public void setShift(String shift) { this.shift = shift; }

    public List<Ticket> getTicketsProcessed() { return ticketsProcessed; }
    public void setTicketsProcessed(List<Ticket> t) { this.ticketsProcessed = t; }
}