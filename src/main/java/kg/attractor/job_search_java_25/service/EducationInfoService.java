package kg.attractor.job_search_java_25.service;

import kg.attractor.job_search_java_25.dto.resumeDtos.nested.EducationInfoUpsertDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.nested.EducationInfoDto;

public interface EducationInfoService {
    EducationInfoDto getEducationInfoById(Long id);

    void create(EducationInfoUpsertDto dto, Long resumeId, Long userId);
}
