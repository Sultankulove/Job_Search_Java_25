package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dao.VacancyDao;
import kg.attractor.job_search_java_25.dto.VacancyShortDto;
import kg.attractor.job_search_java_25.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    private final VacancyDao vacancyDao;
    public List<VacancyShortDto>getShortVacanciesList(Long employerId) {

        List<Vacancy> vacancies = vacancyDao.getVacancies(employerId);
        List<VacancyShortDto> shortVacancies = new ArrayList<>();

        return List.of();
    }
}
