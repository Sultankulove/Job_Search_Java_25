package kg.attractor.job_search_java_25.exceptions.advice;

import jakarta.validation.ConstraintViolationException;
import kg.attractor.job_search_java_25.exceptions.*;
import kg.attractor.job_search_java_25.exceptions.types.BadRequestException;
import kg.attractor.job_search_java_25.exceptions.types.ConflictException;
import kg.attractor.job_search_java_25.exceptions.types.ForbiddenException;
import kg.attractor.job_search_java_25.exceptions.types.NotFoundException;
import kg.attractor.job_search_java_25.service.ErrorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {
    private final ErrorService errorService;

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseBody> handleNotFound(NotFoundException ex) {
        log.warn("404 NotFound [{}]: {}", rid(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseBody.builder()
                        .title("Not Found")
                        .response(Map.of("errors:", List.of(ex.getMessage())))
                        .build());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponseBody> handleForbidden(ForbiddenException ex) {
        log.warn("403 Forbidden [{}]: {}", rid(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(errorService.fromException(ex));
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handleConflict(ConflictException ex) {
        // message вида "email" или "phoneNumber"
        return Map.of("title","Conflict",
                "response", Map.of(ex.getMessage(), List.of("already in use")));
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseBody> handleBadRequest(BadRequestException ex) {
        log.warn("400 BadRequest [{}]: {}", rid(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorService.fromException(ex));
    }





    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseBody> handleInvalidBody(MethodArgumentNotValidException ex) {
        log.debug("400 Validation [{}]: {}", rid(), ex.getMessage());
        return ResponseEntity.badRequest().body(errorService.fromBindingResult(ex.getBindingResult()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseBody> handleInvalidParams(ConstraintViolationException ex) {
        log.debug("400 ConstraintViolation [{}]: {}", rid(), ex.getMessage());
        var details = ex.getConstraintViolations().stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        v -> v.getPropertyPath().toString(),
                        java.util.stream.Collectors.mapping(
                                cv -> cv.getMessage(), java.util.stream.Collectors.toList()
                        )));
        return ResponseEntity.badRequest().body(ErrorResponseBody.builder()
                .title("Validation errors")
                .response(details.isEmpty() ? Map.of("errors:", List.of("Invalid parameters")) : details)
                .build());
    }


    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            MissingServletRequestParameterException.class,
            MissingServletRequestPartException.class
    })
    public ResponseEntity<ErrorResponseBody> handleBadRequestTechnical(Exception ex) {
        log.debug("400 BadRequest (technical) [{}]: {}", rid(), ex.getMessage());
        return ResponseEntity.badRequest().body(errorService.fromException(ex));
    }


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponseBody> handleMaxUpload(MaxUploadSizeExceededException ex) {
        log.debug("413 PayloadTooLarge [{}]: {}", rid(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(ErrorResponseBody.builder()
                .title("File too large")
                .response(Map.of("errors", List.of("Uploaded file exceeds size limit")))
                .build());
    }


    @ExceptionHandler(org.springframework.dao.EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseBody handleEmptyResult(EmptyResultDataAccessException ex) {
        return ErrorResponseBody.builder()
                .title("Not Found")
                .response(Map.of("errors", List.of("Resource not found")))
                .build();
    }

    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handleConflict(DataIntegrityViolationException ex) {
        String msg = Optional.of(ex.getMostSpecificCause())
                .map(Throwable::getMessage).orElse("");
        String field = "unique";
        if (msg.toUpperCase().contains("EMAIL")) field = "email";
        else if (msg.toUpperCase().contains("PHONE")) field = "phoneNumber";

        return Map.of(
                "title", "Conflict",
                "response", Map.of(
                        field, List.of("already in use")
                )
        );
    }



    private String rid() { return MDC.get("requestId"); }

}
