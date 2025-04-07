package org.example.api.controller.exception;

import org.apache.coyote.BadRequestException;

public class InvalidFileFormatException extends BadRequestException {
    public InvalidFileFormatException() {
        super("Invalid file format. Only .txt allowed.");
    }
}
