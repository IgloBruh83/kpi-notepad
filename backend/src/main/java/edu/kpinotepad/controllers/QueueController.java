package edu.kpinotepad.controllers;

import edu.kpinotepad.dto.*;
import edu.kpinotepad.services.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/queue")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:4173"})
public class QueueController {
    private final QueueService queueService;

    @GetMapping("/subject/{subjectId}")
    public List<QueueDTO> getQueue(@PathVariable Long subjectId) {
        return queueService.getActiveQueueBySubject(subjectId);
    }

    @GetMapping("/all")
    public MultiSubjectQueueDTO getAllQueues() {
        return queueService.getAllGeneralQueues();
    }

    @PostMapping("/add")
    public QueueDTO addToQueue(@RequestBody QueueCreateRequest request) {
        return queueService.createEntry(request);
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Void> completeTask(@PathVariable Long id) {
        queueService.markAsCompleted(id);
        return ResponseEntity.ok().build();
    }
}