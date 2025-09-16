package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dto.resumeDtos.nested.contactDtos.ContactTypeDto;
import kg.attractor.job_search_java_25.repository.ContactTypeRepository;
import kg.attractor.job_search_java_25.service.ContactTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactTypeServiceImpl implements ContactTypeService {
    private final ContactTypeRepository contactTypeRepository;

    @Override
    public List<ContactTypeDto> findAll() {
        return contactTypeRepository.findAll(Sort.by(Sort.Direction.ASC, "type"))
                .stream()
                .map(ct -> ContactTypeDto.builder()
                        .id(ct.getId())
                        .name(ct.getType())
                        .build())
                .toList();
    }
}
