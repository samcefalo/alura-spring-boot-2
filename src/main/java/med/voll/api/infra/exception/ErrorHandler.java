package med.voll.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> handle404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorData>> handle400(MethodArgumentNotValidException e) {
        List<ValidationErrorData> errors = e.getFieldErrors().stream()
                .map(ValidationErrorData::new)
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errors);
    }

    public record ValidationErrorData(
            String field,
            String message) {

        public ValidationErrorData(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
