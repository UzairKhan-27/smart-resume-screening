package com.uzair.smart_resume_screening.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PersonNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handlePersonNotFound(PersonNotFoundException ex, WebRequest request){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,ex.getMessage());
        problemDetail.setTitle("Bad Request");
        return problemDetail;
    }

    @ExceptionHandler(JobNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleJobNotFound(JobNotFoundException ex,WebRequest request){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,ex.getMessage());
        problemDetail.setTitle("Bad Request");
        return problemDetail;
    }

    @ExceptionHandler(JobApplicationNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleJobApplicationNotFound(JobApplicationNotFoundException ex, WebRequest request){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,ex.getMessage());
        problemDetail.setTitle("Bad Request");
        return problemDetail;
    }

    @ExceptionHandler(NonResumeFileUploadedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleNonResumeFileUploaded(NonResumeFileUploadedException ex,WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,ex.getMessage());
        problemDetail.setTitle("Bad Request");
        return problemDetail;
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleEmailAlreadyInUse(EmailAlreadyInUseException ex,WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,ex.getMessage());
        problemDetail.setTitle("Bad Request");
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleValidationException(MethodArgumentNotValidException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Validation Failed");
        problemDetail.setDetail("One or more fields are invalid. See errors for details.");
        problemDetail.setProperty("errors", e.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (msg1, msg2) -> msg1 // in case of duplicate field errors
                ))
        );
        return problemDetail;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleConstraintViolationException(ConstraintViolationException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Constraint Violation");
        problemDetail.setDetail("One or more constraints were violated. See errors for details.");

        // Convert violations into a field-to-message map
        Map<String, String> errors = e.getConstraintViolations()
                .stream()
                .collect(Collectors.toMap(
                        violation -> {
                            String path = violation.getPropertyPath().toString();
                            // Trim method name if present (for path like "methodName.argName")
                            int dotIndex = path.indexOf('.');
                            return (dotIndex != -1) ? path.substring(dotIndex + 1) : path;
                        },
                        ConstraintViolation::getMessage,
                        (msg1, msg2) -> msg1 // handle duplicate keys gracefully
                ));

        problemDetail.setProperty("errors", errors);
        return problemDetail;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrity(DataIntegrityViolationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("Data Integrity Violation");
        problemDetail.setDetail("Database constraint violated: " + ex.getMostSpecificCause().getMessage());
        return problemDetail;
    }


}