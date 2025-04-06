package org.example.api.controllers.v1;

import org.example.api.controllers.v1.dto.BatchRequest;
import org.example.api.controllers.v1.dto.BatchResponse;
import org.example.api.controllers.v1.parser.TxtBatchParser;
import org.example.api.domain.entity.Batch;
import org.example.api.domain.service.BatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.example.api.controllers.v1.mapper.BatchMapper.map;


@RestController
@RequestMapping("/api/v1/batches")
public class BatchController {

    private final BatchService service;
    private final TxtBatchParser parser;

    public BatchController(BatchService service, TxtBatchParser parser) {
        this.service = service;
        this.parser = parser;
    }


    @PostMapping("/upload")
    public ResponseEntity<BatchResponse> upload(@RequestParam("file") MultipartFile file) throws IOException {
        BatchRequest request = parser.parseAndValidate(file);
        Batch batch = service.createBatch(request);
        service.processBatchCards(batch, request.getCards());
        return new ResponseEntity<>(map(batch), HttpStatus.CREATED);
    }
}