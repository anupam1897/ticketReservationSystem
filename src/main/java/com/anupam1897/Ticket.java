package com.anupam1897;
public class Ticket {
    private String ticketNo;
    private String startingLocation;
    private String destinationLocation;
    private int passengerNo;
    private double fare;
    private boolean cancelled = false;

    public Ticket(String ticketNo, String startingLocation,
                  String destinationLocation, int passengerNo, double fare) {
        this.ticketNo            = ticketNo;
        this.startingLocation    = startingLocation;
        this.destinationLocation = destinationLocation;
        this.passengerNo         = passengerNo;
        this.fare                = fare;
    }

    public String getTicketNo()            { return ticketNo; }
    public String getStartingLocation()    { return startingLocation; }
    public String getDestinationLocation() { return destinationLocation; }
    public int    getPassengerNo()         { return passengerNo; }
    public boolean isCancelled()           { return cancelled; }

    public double paymentAmount() {
        return fare;
    }

    public void cancelTicket() {
        if (cancelled) {
            System.out.println("Ticket " + ticketNo + " is already cancelled.");
        } else {
            cancelled = true;
            System.out.println("Ticket cancelled: " + ticketNo
                + " (" + startingLocation + " -> " + destinationLocation + ")");
        }
    }

    @Override
    public String toString() {
        return "Ticket[" + ticketNo + "] "
            + startingLocation + " -> " + destinationLocation
            + " | Fare: Rs." + fare
            + " | Status: " + (cancelled ? "Cancelled" : "Active");
    }
}