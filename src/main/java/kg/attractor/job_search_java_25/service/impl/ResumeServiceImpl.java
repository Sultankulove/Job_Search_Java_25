package kg.attractor.job_search_java_25.service.impl;

import kg.attractor.job_search_java_25.dto.ResumeDto;
import kg.attractor.job_search_java_25.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    @Override
    public ResponseEntity<List<ResumeDto>> findResumeCategory(String name) {
        // TODO Должен возвращать список резюме по категории (категорию находим по имени)
        return null;
    }

    @Override
    public ResponseEntity<ResumeDto> findAllResume() {
        // TODO Должен возвращать весь список резюме
        return null;
    }

    @Override
    public void createResume(ResumeDto resumeDto) {
        // TODO Создаем новое резюме по входным данным
    }

    @Override
    public void deleteResume(long id) {
        // TODO Удалить резюме по id
    }

    @Override
    public void editResume(long id, ResumeDto resumeDto) {
        // TODO Редактировать резюме. Находим нужный резюме по id. И перезаписываем его.
    }
}
