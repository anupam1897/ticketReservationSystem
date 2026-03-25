package com.anupam1897.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String transactionId;

    private double  amount;
    private String  paymentMethod;   // CARD, UPI, CASH, NET_BANKING
    private LocalDateTime paymentDate;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDING;

    @OneToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    public enum PaymentStatus {
        PENDING, COMPLETED, FAILED, REFUNDED
    }

    public Payment() {
        this.paymentDate = LocalDateTime.now();
    }

    public Payment(String transactionId, double amount,
                   String paymentMethod, Ticket ticket) {
        this.transactionId = transactionId;
        this.amount        = amount;
        this.paymentMethod = paymentMethod;
        this.ticket        = ticket;
        this.paymentDate   = LocalDateTime.now();
    }

    // ---- Business Methods ----
    public void complete() { this.status = PaymentStatus.COMPLETED; }
    public void fail()     { this.status = PaymentStatus.FAILED;    }
    public void refund()   { this.status = PaymentStatus.REFUNDED;  }

    // ---- Getters & Setters ----
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String t) { this.transactionId = t; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String p) { this.paymentMethod = p; }

    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime d) { this.paymentDate = d; }

    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }

    public Ticket getTicket() { return ticket; }
    public void setTicket(Ticket ticket) { this.ticket = ticket; }
}