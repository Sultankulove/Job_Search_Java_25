package kg.attractor.job_search_java_25.dto.resumeDtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import kg.attractor.job_search_java_25.dto.CategoryDtos.CategoryDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.nested.EducationInfoDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.nested.WorkExperienceInfoDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.nested.contactDtos.ContactInfoDto;
import kg.attractor.job_search_java_25.dto.userDtos.UserViewProfileDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeAllInfoDto {
    private UserViewProfileDto user;
    private String name;
    private CategoryDto category;
    private BigDecimal salary;
    private boolean active;
    private LocalDateTime createdDate;
    private LocalDateTime updateTime;
    private List<WorkExperienceInfoDto> workExperiences;
    private List<EducationInfoDto> educationInfos;
    private List<ContactInfoDto> contactInfos;
}
