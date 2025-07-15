package kg.attractor.job_search_java_25.service;

import kg.attractor.job_search_java_25.dto.EmployerDto;
import org.springframework.http.ResponseEntity;

public interface EmployerService {
    ResponseEntity<EmployerDto> findEmployerByName(String name);
}
