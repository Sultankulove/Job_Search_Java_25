package kg.attractor.job_search_java_25.service;

import kg.attractor.job_search_java_25.dto.resumeDtos.nested.WorkExperienceInfoDto;

public interface WorkExperienceInfoService {
    void create(WorkExperienceInfoDto workExperienceInfoDto, Long resumeId, Long userId);

    WorkExperienceInfoDto getWorkExperienceInfoById(Long id);
}
