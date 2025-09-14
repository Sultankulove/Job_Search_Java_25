package kg.attractor.job_search_java_25.mappers;

import kg.attractor.job_search_java_25.dto.resumeDtos.nested.contactDtos.ContactInfoDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.nested.contactDtos.ContactTypeDto;
import kg.attractor.job_search_java_25.model.ContactType;
import org.springframework.stereotype.Component;

@Component
public class ContactTypeMapper {
    public ContactTypeDto toDto(ContactType t) {
        if (t == null) return null;
        var dto = new ContactTypeDto();
        dto.setId(t.getId());
        dto.setName(t.getType());
        dto.setContacts(t.getContacts()
                .stream()
                .map(contactInfo -> {
                    ContactInfoDto contactInfoDto = new ContactInfoDto();
                    contactInfoDto.setContactValue(contactInfo.getContactValue());
                    contactInfoDto.setTypeId(contactInfo.getType().getId());
                    return contactInfoDto;
                })
                .toList()
        );
        return dto;
    }
}
