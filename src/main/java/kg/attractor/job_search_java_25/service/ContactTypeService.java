package kg.attractor.job_search_java_25.service;

import kg.attractor.job_search_java_25.dto.resumeDtos.nested.contactDtos.ContactTypeDto;

import java.util.List;

public interface ContactTypeService {
    List<ContactTypeDto> findAll();
}
