package com.anupam1897;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Passenger {
    private String name;
    private int    age;
    private String address;
    private int    passengerId;

    private static int idCounter = 1;
    private List<Ticket> tickets = new ArrayList<>();

    public Passenger(String name, int age, String address) {
        this.name        = name;
        this.age         = age;
        this.address     = address;
        this.passengerId = idCounter++;
    }

    public void searchTrains(RailwayDatabase db) {
        System.out.println("\n--- Available Trains ---");
        List<Train> trains = db.getAllTrains();
        if (trains.isEmpty()) {
            System.out.println("No trains available.");
        } else {
            for (Train t : trains) {
                System.out.println(t);
            }
        }
    }

    public void viewSchedule() {
        System.out.println("\n--- Schedule for " + name + " ---");
        if (tickets.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            for (Ticket t : tickets) {
                System.out.println(t);
            }
        }
    }

    public Ticket reserveSeat(Train train, String from, String to, double fare) {
        String ticketNo = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Ticket ticket = new Ticket(ticketNo, from, to, passengerId, fare);
        tickets.add(ticket);
        System.out.println("Seat reserved for " + name
            + " on " + train + " | Ticket: " + ticketNo);
        return ticket;
    }

    public void purchaseTicket(Ticket ticket, Payment payment) {
        System.out.println("Ticket purchased: " + ticket.getTicketNo()
            + " | Amount paid: Rs." + payment.getAmount());
    }

    public void cancelTicket(Ticket ticket) {
        tickets.remove(ticket);
        ticket.cancelTicket();
    }

    public void makePayment(double amount) {
        Payment payment = new Payment(amount);
        System.out.println(name + " made " + payment);
    }

    public String getName()      { return name; }
    public int    getPassengerId() { return passengerId; }
}