package kg.attractor.job_search_java_25.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeDto {

    @NotNull(message = "ID Соискателя оюязателен")
    private Long applicantId;

    @NotBlank(message = "Название резюме обязательно")
    private String name;

    @NotNull(message = "Выбери категорию")
    private Long categoryId;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "Зарплата должна быть положительной")
    private Double salary;

    private Boolean isActive;
    private LocalDateTime createdDate;
    private LocalDateTime updatedTime;

    private List<EducationInfoDto> education;
    private List<WorkExperienceInfoDto> experience;
    private List<ContactInfoDto> contacts;
}