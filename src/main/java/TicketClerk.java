public class TicketClerk {
    private String         name;
    private String         location;
    private RailwayDatabase db;

    public TicketClerk(String name, String location, RailwayDatabase db) {
        this.name     = name;
        this.location = location;
        this.db       = db;
    }

    public Ticket reserveSeat(Passenger passenger, Train train,
                              String from, String to, double fare) {
        Ticket ticket = passenger.reserveSeat(train, from, to, fare);
        db.addTicket(ticket);
        return ticket;
    }

    public Ticket reserveTicket(Passenger passenger, Train train,
                                String from, String to, double fare) {
        return reserveSeat(passenger, train, from, to, fare);
    }

    public void cancelTicket(Passenger passenger, Ticket ticket) {
        passenger.cancelTicket(ticket);
        db.removeTicket(ticket.getTicketNo());
    }

    public void receivePayment(double amount) {
        System.out.println("Clerk " + name + " at " + location
            + " received payment: Rs." + amount);
        db.setResponse("Payment of Rs." + amount + " received by " + name);
    }
}