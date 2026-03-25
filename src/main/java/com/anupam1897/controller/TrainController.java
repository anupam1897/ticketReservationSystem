package com.anupam1897.controller;

import com.anupam1897.model.Train;
import com.anupam1897.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/trains")
public class TrainController {

    @Autowired
    private TrainService trainService;

    @GetMapping
    public ResponseEntity<List<Train>> searchTrains(
            @RequestParam(required = false) String source,
            @RequestParam(required = false) String destination) {
        return ResponseEntity.ok(trainService.searchTrains(source, destination));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Train> getTrainById(@PathVariable Long id) {
        return ResponseEntity.ok(trainService.getTrainById(id));
    }

    @PostMapping
    public ResponseEntity<Train> addTrain(@RequestBody Train train) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(trainService.addTrain(train));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrain(@PathVariable Long id) {
        trainService.deleteTrain(id);
        return ResponseEntity.noContent().build();
    }
}