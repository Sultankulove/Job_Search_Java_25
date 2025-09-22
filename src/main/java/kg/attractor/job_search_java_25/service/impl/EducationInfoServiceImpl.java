package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dto.resumeDtos.nested.EducationInfoUpsertDto;
import kg.attractor.job_search_java_25.dto.resumeDtos.nested.EducationInfoDto;
import kg.attractor.job_search_java_25.exceptions.types.ForbiddenException;
import kg.attractor.job_search_java_25.model.EducationInfo;
import kg.attractor.job_search_java_25.model.Resume;
import kg.attractor.job_search_java_25.repository.EducationInfoRepository;
import kg.attractor.job_search_java_25.service.EducationInfoService;
import kg.attractor.job_search_java_25.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class EducationInfoServiceImpl implements EducationInfoService {
    private final EducationInfoRepository educationInfoRepository;
    private final ResumeService resumeService;

    @Override
    public EducationInfoDto getEducationInfoById(Long id) {

        EducationInfo entity = educationInfoRepository.getEducationInfoById(id);
        EducationInfoDto educationInfoDto = new EducationInfoDto();
        educationInfoDto.setProgram(entity.getProgram());
        educationInfoDto.setInstitution(entity.getInstitution());
        educationInfoDto.setStartDate(entity.getStartDate());
        educationInfoDto.setEndDate(entity.getEndDate());
        educationInfoDto.setDegree(entity.getDegree());
        return educationInfoDto;
    }

    @Override
    public void create(EducationInfoUpsertDto dto, Long resumeId, Long userId) {
        resumeService.checkResumeOwnership(resumeId, userId);


        LocalDate start = dto.getStartDate();
        LocalDate end   = dto.getEndDate();
        if (start != null && end != null && end.isBefore(start)) { var t = start; start = end; end = t; }

        Resume resumeRef = new Resume(); resumeRef.setId(resumeId);
        EducationInfo e = new EducationInfo();

        e.setResume(resumeRef);

        e.setInstitution(dto.getInstitution());
        e.setProgram(dto.getProgram());
        e.setDegree(dto.getDegree());
        e.setStartDate(start);
        e.setEndDate(end);

        educationInfoRepository.save(e);
    }
}
