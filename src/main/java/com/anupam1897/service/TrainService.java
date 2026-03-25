package com.anupam1897.service;

import com.anupam1897.model.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TrainService {

    @Autowired
    private RailwayDatabase railwayDatabase;

    public List<Train> getAllTrains() {
        return railwayDatabase.getAllTrains();
    }

    public Train getTrainById(Long id) {
        return railwayDatabase.getTrainById(id);
    }

    public List<Train> searchTrains(String source, String destination) {
        return railwayDatabase.searchTrains(source, destination);
    }

    public Train addTrain(Train train) {
        return railwayDatabase.addTrain(train);
    }

    public void deleteTrain(Long id) {
        railwayDatabase.deleteTrain(id);
    }
}