package se.lexicon.subscriptionapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("Resource Not Found", ex.getMessage());
        response.put("timestamp", String.valueOf(Instant.now()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleBusinessRuleException(BusinessRuleException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("Business Rule Exception", ex.getMessage());
        response.put("timestamp", String.valueOf(Instant.now()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleException(MethodArgumentNotValidException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("Exception", ex.getMessage());
        response.put("timestamp", String.valueOf(Instant.now()));
        ex.getBindingResult().getFieldErrors().forEach((error) -> {response.put(error.getField(), error.getDefaultMessage());});
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
