package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dto.EmployerDto;
import kg.attractor.job_search_java_25.service.EmployerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployerServiceImpl implements EmployerService {
    @Override
    public ResponseEntity<EmployerDto> findEmployerByName(String name) {
        // TODO Должен возвращать работодателя по имени
        return null;
    }
}
