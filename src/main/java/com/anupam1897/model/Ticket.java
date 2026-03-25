package com.anupam1897.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String ticketNo;

    private String  startingLocation;
    private String  destinationLocation;
    private double  fare;
    private String  seatNumber;
    private LocalDateTime bookingDate;
    private LocalDateTime journeyDate;

    @Enumerated(EnumType.STRING)
    private TicketStatus status = TicketStatus.RESERVED;

    @ManyToOne
    @JoinColumn(name = "passenger_id", nullable = false)
    private Passenger passenger;

    @ManyToOne
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;

    @ManyToOne
    @JoinColumn(name = "clerk_id")
    private TicketClerk bookedByClerk;

    @JsonIgnore
    @OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL)
    private Payment payment;

    public enum TicketStatus {
        RESERVED, PURCHASED, CANCELLED
    }

    public Ticket() {
        this.bookingDate = LocalDateTime.now();
    }

    public Ticket(String ticketNo, String from, String to, double fare,
                  String seatNumber, LocalDateTime journeyDate,
                  Passenger passenger, Train train, TicketClerk clerk) {
        this.ticketNo            = ticketNo;
        this.startingLocation    = from;
        this.destinationLocation = to;
        this.fare                = fare;
        this.seatNumber          = seatNumber;
        this.journeyDate         = journeyDate;
        this.passenger           = passenger;
        this.train               = train;
        this.bookedByClerk       = clerk;
        this.bookingDate         = LocalDateTime.now();
    }

    // ---- Business Methods ----
    public boolean isCancelled() { return status == TicketStatus.CANCELLED; }
    public boolean isPurchased() { return status == TicketStatus.PURCHASED; }
    public boolean isReserved()  { return status == TicketStatus.RESERVED;  }

    public void cancel()   { this.status = TicketStatus.CANCELLED; }
    public void purchase() { this.status = TicketStatus.PURCHASED; }

    // ---- Getters & Setters ----
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTicketNo() { return ticketNo; }
    public void setTicketNo(String ticketNo) { this.ticketNo = ticketNo; }

    public String getStartingLocation() { return startingLocation; }
    public void setStartingLocation(String s) { this.startingLocation = s; }

    public String getDestinationLocation() { return destinationLocation; }
    public void setDestinationLocation(String d) { this.destinationLocation = d; }

    public double getFare() { return fare; }
    public void setFare(double fare) { this.fare = fare; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public LocalDateTime getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDateTime bookingDate) { this.bookingDate = bookingDate; }

    public LocalDateTime getJourneyDate() { return journeyDate; }
    public void setJourneyDate(LocalDateTime journeyDate) { this.journeyDate = journeyDate; }

    public TicketStatus getStatus() { return status; }
    public void setStatus(TicketStatus status) { this.status = status; }

    public Passenger getPassenger() { return passenger; }
    public void setPassenger(Passenger passenger) { this.passenger = passenger; }

    public Train getTrain() { return train; }
    public void setTrain(Train train) { this.train = train; }

    public TicketClerk getBookedByClerk() { return bookedByClerk; }
    public void setBookedByClerk(TicketClerk clerk) { this.bookedByClerk = clerk; }

    public Payment getPayment() { return payment; }
    public void setPayment(Payment payment) { this.payment = payment; }
}