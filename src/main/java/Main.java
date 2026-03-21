public class Main {
    public static void main(String[] args) {

        // Setup database and add trains
        RailwayDatabase db = new RailwayDatabase("DB001");
        db.addTrain(new Train("Express",    "T101"));
        db.addTrain(new Train("Superfast",  "T202"));
        db.addTrain(new Train("Intercity",  "T303"));

        // Create passenger and clerk
        Passenger  passenger = new Passenger("Anupam", 22, "Chennai");
        TicketClerk clerk    = new TicketClerk("Ravi", "Chennai Central", db);

        // Passenger searches for trains
        passenger.searchTrains(db);

        // Clerk reserves a ticket for the passenger
        Train  selectedTrain = db.getAllTrains().get(0);
        Ticket ticket        = clerk.reserveTicket(
                                   passenger, selectedTrain,
                                   "Chennai", "Mumbai", 850.0);

        // View schedule after booking
        passenger.viewSchedule();

        // Passenger pays
        Payment payment = new Payment(ticket.paymentAmount());
        passenger.purchaseTicket(ticket, payment);
        clerk.receivePayment(payment.getAmount());

        // Cancel the ticket
        clerk.cancelTicket(passenger, ticket);

        // View schedule after cancellation
        passenger.viewSchedule();

        System.out.println("\n[DB Last Response] " + db.getResponse());
    }
}
