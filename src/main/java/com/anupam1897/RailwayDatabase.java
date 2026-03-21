package com.anupam1897;
import java.util.ArrayList;
import java.util.List;

public class RailwayDatabase {
    private String id;
    private String response;

    private List<Train>  trains  = new ArrayList<>();
    private List<Ticket> tickets = new ArrayList<>();

    public RailwayDatabase(String id) {
        this.id = id;
    }

    public void addTrain(Train train) {
        trains.add(train);
        response = "Train added: " + train.getTrainId();
        System.out.println("[DB] " + response);
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
        response = "Ticket stored: " + ticket.getTicketNo();
        System.out.println("[DB] " + response);
    }

    public boolean removeTicket(String ticketNo) {
        boolean removed = tickets.removeIf(t -> t.getTicketNo().equals(ticketNo));
        response = removed
            ? "Ticket removed: " + ticketNo
            : "Ticket not found: " + ticketNo;
        System.out.println("[DB] " + response);
        return removed;
    }

    public List<Train> getAllTrains() {
        return trains;
    }

    public List<Ticket> getAllTickets() {
        return tickets;
    }

    public String getId()       { return id; }
    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }
}