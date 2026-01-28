package com.shop.ecommerce.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	 /* =======================
    ResponseStatusException
    ======================= */
 @ExceptionHandler(ResponseStatusException.class)
 public ResponseEntity<?> handleResponseStatus(ResponseStatusException ex) {

     Map<String, Object> body = new HashMap<>();
     body.put("timestamp", LocalDateTime.now());
     body.put("status", ex.getStatusCode().value());
     body.put("error", ex.getStatusCode().toString());
     body.put("message", ex.getReason());

     return ResponseEntity
             .status(ex.getStatusCode())
             .body(body);
 }

 /* =======================
    @Valid DTO validation
    ======================= */
 @ExceptionHandler(MethodArgumentNotValidException.class)
 public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {

     Map<String, String> errors = new HashMap<>();
     ex.getBindingResult()
       .getFieldErrors()
       .forEach(error ->
           errors.put(error.getField(), error.getDefaultMessage())
       );

     Map<String, Object> body = new HashMap<>();
     body.put("timestamp", LocalDateTime.now());
     body.put("status", 400);
     body.put("error", "Validation Failed");
     body.put("message", errors);

     return ResponseEntity
             .badRequest()
             .body(body);
 }

 /* =======================
    @RequestParam / @PathVariable validation
    ======================= */
 @ExceptionHandler(ConstraintViolationException.class)
 public ResponseEntity<?> handleConstraintViolation(ConstraintViolationException ex) {

     Map<String, Object> body = new HashMap<>();
     body.put("timestamp", LocalDateTime.now());
     body.put("status", 400);
     body.put("error", "Validation Failed");
     body.put("message", ex.getMessage());

     return ResponseEntity
             .badRequest()
             .body(body);
 }

 /* =======================
    RuntimeException (fallback)
    ======================= */
 @ExceptionHandler(RuntimeException.class)
 public ResponseEntity<?> handleRuntime(RuntimeException ex) {

     Map<String, Object> body = new HashMap<>();
     body.put("timestamp", LocalDateTime.now());
     body.put("status", 500);
     body.put("error", "Internal Server Error");
     body.put("message", ex.getMessage());

     return ResponseEntity
             .status(HttpStatus.INTERNAL_SERVER_ERROR)
             .body(body);
 }
}
