package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dto.resumeDtos.nested.WorkExperienceInfoDto;
import kg.attractor.job_search_java_25.model.WorkExperienceInfo;
import kg.attractor.job_search_java_25.repository.WorkExperienceInfoRepository;
import kg.attractor.job_search_java_25.service.WorkExperienceInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkExperienceInfoServiceImpl implements WorkExperienceInfoService {
    private final WorkExperienceInfoRepository workExperienceInfoRepository;

    public void create(WorkExperienceInfoDto workExperienceInfoDto, Long resumeId) {
        WorkExperienceInfo model = new WorkExperienceInfo();
        model.getResume().setId(resumeId);
        model.setYears(workExperienceInfoDto.getYears());
        model.setCompanyName(workExperienceInfoDto.getCompanyName());
        model.setPosition(workExperienceInfoDto.getPosition());
        model.setResponsibilities(workExperienceInfoDto.getResponsibilities());
        workExperienceInfoRepository.save(model);
    }
}
