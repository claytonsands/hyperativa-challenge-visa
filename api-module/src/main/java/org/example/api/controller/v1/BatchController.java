package org.example.api.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.api.controller.exception.InvalidFileFormatException;
import org.example.api.controller.v1.dto.BatchRequest;
import org.example.api.controller.v1.dto.BatchResponse;
import org.example.api.controller.v1.parser.TxtBatchParser;
import org.example.api.domain.entity.Batch;
import org.example.api.domain.service.BatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

import static org.example.api.controller.v1.mapper.BatchMapper.map;

@Tag(name = "Batch")
@RestController
@RequestMapping("/api/v1/batches")
public class BatchController {

    private final BatchService service;
    private final TxtBatchParser parser;

    public BatchController(BatchService service, TxtBatchParser parser) {
        this.service = service;
        this.parser = parser;
    }

    @Operation(
            summary = "Upload .txt file with batch info",
            description = "Receives a .txt file in positional format",
            responses = {
                    @ApiResponse(responseCode = "200", description = "File processed successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid file")
            }
    )
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<BatchResponse> uploadFile(
            @Parameter(description = "TXT file", required = true)
            @RequestPart("file") MultipartFile file) throws IOException {
        if (file.isEmpty() || !Objects.requireNonNull(file.getOriginalFilename()).endsWith(".txt")) {
            throw new InvalidFileFormatException();
        }

        BatchRequest request = parser.parseAndValidate(file);
        Batch batch = service.createBatch(request);
        service.processBatchCards(batch, request.getCards());
        return new ResponseEntity<>(map(batch), HttpStatus.CREATED);
    }
}