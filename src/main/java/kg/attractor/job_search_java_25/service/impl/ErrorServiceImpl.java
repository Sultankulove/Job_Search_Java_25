package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.exceptions.ErrorResponseBody;
import kg.attractor.job_search_java_25.service.ErrorService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.*;

@Service
public class ErrorServiceImpl implements ErrorService {
    @Override
    public ErrorResponseBody fromBindingResult(BindingResult br) {
        Map<String, List<String>> reasons = new LinkedHashMap<>();
        br.getFieldErrors().forEach(err -> {
            if (err.getDefaultMessage() == null) return;
            reasons.computeIfAbsent(err.getField(), k -> new ArrayList<>())
                    .add(err.getDefaultMessage());
        });
        return ErrorResponseBody.builder()
                .title("Validation errors")
                .response(reasons.isEmpty()
                        ? Map.of("errors", List.of("Invalid request"))
                        : reasons)
                .build();
    }

    @Override
    public ErrorResponseBody fromException(Throwable e) {
        String msg = Optional.ofNullable(e.getMessage()).orElse(e.getClass().getSimpleName());
        return ErrorResponseBody.builder()
                .title(msg)
                .response(Map.of("errors", List.of(msg)))
                .build();
    }
}
