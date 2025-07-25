package kg.attractor.job_search_java_25.service;

import kg.attractor.job_search_java_25.exceptions.ErrorResponseBody;
import org.springframework.validation.BindingResult;

public interface ErrorService {
    ErrorResponseBody makeResponse (BindingResult bindingResult);
}
