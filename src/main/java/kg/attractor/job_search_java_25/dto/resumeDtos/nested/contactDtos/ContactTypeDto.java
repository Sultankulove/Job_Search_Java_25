package kg.attractor.job_search_java_25.dto.resumeDtos.nested.contactDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactTypeDto {

    private Long id;

    private String name;

    private List<ContactInfoDto> contacts;
}
