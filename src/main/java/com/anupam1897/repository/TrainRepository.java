package com.anupam1897.repository;

import com.anupam1897.model.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {
    Optional<Train> findByTrainNumber(String trainNumber);
    List<Train> findBySourceAndDestination(String source, String destination);
    List<Train> findBySource(String source);
    List<Train> findByDestination(String destination);
}