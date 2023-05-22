package io.task.template.api;

import cz.jirutka.rsql.parser.RSQLParserException;
import io.task.template.api.v1.resources.ConstraintViolationResource;
import io.task.template.services.exceptions.BusinessException;
import io.task.template.api.v1.resources.ErrorResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<ErrorResource> handleConstraintViolationException(final ConstraintViolationException e) {
        final var violations = e.getConstraintViolations().stream()
                .map(violation -> new ConstraintViolationResource()
                        .property(violation.getPropertyPath().toString())
                        .type("ConstraintViolation")
                        .message(violation.getMessage())
                )
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResource().message(e.getMessage()).violations(violations));
    }

    @ExceptionHandler(NoSuchElementException.class)
    ResponseEntity<ErrorResource> handleNoSuchElementException(final NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResource().message("Item not found"));
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    ResponseEntity<ErrorResource> handleInvalidDataAccessApiUsageException(final InvalidDataAccessApiUsageException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResource().message("Request data is not valid").details(e.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<ErrorResource> handleDataIntegrityViolationException(final DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResource().message(e.getMessage()));
    }

    @ExceptionHandler(BusinessException.class)
    ResponseEntity<ErrorResource> handleBusinessException(final BusinessException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(new ErrorResource().message(e.getMessage()).code(e.getCode()));
    }

    @ExceptionHandler(PropertyReferenceException.class)
    ResponseEntity<ErrorResource> handlePropertyReferenceException(final PropertyReferenceException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResource().message(e.getMessage()));
    }

    @ExceptionHandler(RSQLParserException.class)
    ResponseEntity<ErrorResource> handleRSQLParserException(final RSQLParserException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResource().message("Error parsing filter R SQL").details(e.getMessage()));
    }
}
