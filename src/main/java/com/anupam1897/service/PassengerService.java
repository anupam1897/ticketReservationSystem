package com.anupam1897.service;

import com.anupam1897.model.Passenger;
import com.anupam1897.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PassengerService {

    @Autowired
    private RailwayDatabase railwayDatabase;

    public Passenger register(Passenger passenger) {
        return railwayDatabase.addPassenger(passenger);
    }

    public Passenger getById(Long id) {
        return railwayDatabase.getPassengerById(id);
    }

    public List<Passenger> getAllPassengers() {
        return railwayDatabase.getAllPassengers();
    }

    public List<Ticket> getSchedule(Long passengerId) {
        return railwayDatabase.getSchedule(passengerId);
    }

    public List<Ticket> getAllTickets(Long passengerId) {
        return railwayDatabase.getTicketsByPassenger(passengerId);
    }
}