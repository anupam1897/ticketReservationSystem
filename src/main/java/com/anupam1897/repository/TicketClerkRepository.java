package com.anupam1897.repository;

import com.anupam1897.model.TicketClerk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketClerkRepository extends JpaRepository<TicketClerk, Long> {
    Optional<TicketClerk> findByEmployeeId(String employeeId);
    List<TicketClerk> findByStation(String station);
}