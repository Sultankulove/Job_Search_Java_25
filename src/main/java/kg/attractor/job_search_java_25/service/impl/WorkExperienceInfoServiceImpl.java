package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dto.resumeDtos.nested.WorkExperienceInfoDto;
import kg.attractor.job_search_java_25.exceptions.types.ForbiddenException;
import kg.attractor.job_search_java_25.model.Resume;
import kg.attractor.job_search_java_25.model.WorkExperienceInfo;
import kg.attractor.job_search_java_25.repository.WorkExperienceInfoRepository;
import kg.attractor.job_search_java_25.service.ResumeService;
import kg.attractor.job_search_java_25.service.WorkExperienceInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkExperienceInfoServiceImpl implements WorkExperienceInfoService {
    private final WorkExperienceInfoRepository workExperienceInfoRepository;
    private final ResumeService resumeService;

    @Override
    public void create(WorkExperienceInfoDto dto, Long resumeId, Long userId) {
        resumeService.checkResumeOwnership(resumeId, userId);

        Resume resumeRef = new Resume(); resumeRef.setId(resumeId);
        WorkExperienceInfo e = new WorkExperienceInfo();
        e.setResume(resumeRef);
        e.setYears(dto.getYears());
        e.setCompanyName(dto.getCompanyName());
        e.setPosition(dto.getPosition());
        e.setResponsibilities(dto.getResponsibilities());

        workExperienceInfoRepository.save(e);
    }

    @Override
    public WorkExperienceInfoDto getWorkExperienceInfoById(Long id) {
        return workExperienceInfoRepository.getWorkExperienceInfoById(id);
    }
}
