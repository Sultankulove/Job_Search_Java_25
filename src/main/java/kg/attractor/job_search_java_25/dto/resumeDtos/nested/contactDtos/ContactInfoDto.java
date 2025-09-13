package kg.attractor.job_search_java_25.dto.resumeDtos.nested.contactDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactInfoDto {

    private Long typeId;
    private String contactValue;

}
