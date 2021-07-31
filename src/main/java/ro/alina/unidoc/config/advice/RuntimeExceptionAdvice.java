package ro.alina.unidoc.config.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Priority;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ControllerAdvice(annotations = RestController.class)
@Priority(1)
public class RuntimeExceptionAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(final RuntimeException exception) {

        Map<String, String> errorMessage = new HashMap<>();
        errorMessage.put("message", exception.getMessage());
        errorMessage.put("cause",
                         Optional.ofNullable(exception.getCause())
                                 .map(Throwable::getMessage)
                                 .orElse(""));
        errorMessage.put("type", exception.getClass().getSimpleName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorMessage);
    }
}
