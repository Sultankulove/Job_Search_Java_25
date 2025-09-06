package kg.attractor.job_search_java_25.repository;


import kg.attractor.job_search_java_25.dto.VacancyDto;
import kg.attractor.job_search_java_25.dto.VacancyShortDto;
import kg.attractor.job_search_java_25.model.Category;
import kg.attractor.job_search_java_25.model.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {


    Page<Vacancy> findAllByAuthor_Id(Long authorId, Pageable p);

    @Modifying
    @Transactional
    @Query("update Vacancy v set v.updateTime = CURRENT_TIMESTAMP where v.id = :id")
    void updateTime(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("""
       update Vacancy v
          set v.name        = :name,
              v.description = :description,
              v.category  = :Category,
              v.salary      = :salary,
              v.expFrom     = :expFrom,
              v.expTo       = :expTo,
              v.isActive    = :isActive,
              v.updateTime  = CURRENT_TIMESTAMP
        where v.id = :id and v.author.id = :userId
       """)
    int editVacancy(@Param("name") String name,
                     @Param("description") String description,
                     @Param("category") Category category,
                     @Param("salary") Float salary,
                     @Param("expFrom") Integer expFrom,
                     @Param("expTo") Integer expTo,
                     @Param("isActive") Boolean isActive,
                     @Param("id") Long id,
                     @Param("userId") Long userId);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Vacancy v set v.isActive = :isActive where v.id = :vacancyId")
    int vacancyIsActive(@Param("vacancyId") Long vacancyId,
                        @Param("isActive") Boolean isActive);



    void deleteById(Long id);

    long deleteByIdAndAuthorId(Long id, Long authorId);


    @Query("select v.author.id from Vacancy v where v.id = :vacancyId")
    Long getOwnerId(@Param("vacancyId") Long vacancyId);

    @Query("""
            select new kg.attractor.job_search_java_25.dto.VacancyShortDto(
                        v.name,
                        v.updateTime
                  )
                  from Vacancy v
                  where v.isActive = true
                  order by v.updateTime desc
            """)
    List<VacancyShortDto> getActiveShortVacancies();

    @Transactional(readOnly = true)
    @Query("""
    select new kg.attractor.job_search_java_25.dto.VacancyShortDto(
        v.name,
        v.updateTime
    )
    from Vacancy v
    where v.author.id = :authorId
    order by v.updateTime desc
    """)
    List<VacancyShortDto> getShortVacanciesByAuthorId(@Param("authorId") Long authorId);

    @Modifying
    @Query(
            """
        UPDATE Vacancy v SET v.name = :name,
        v.description = :description,
        v.category = :Category,
        v.salary = :salary,
        v.expFrom  = :expFrom,
        v.expTo  = :expTo,
        v.isActive  = :isActive,
        v.updateTime  = CURRENT_TIMESTAMP where v.id = :vacancyId and v.author.id = :userId
"""
    )
    void saveVacancy_IdUser_Id(Vacancy vacancy, Long vacancyId, Long userId);

    Optional<Vacancy> findVacancyById(Long vacancyId);


    List<VacancyDto> findVacanciesById(Long id);

    List<Vacancy> findAllByAuthor_Id(Long userId);

    Page<Vacancy> findByCategory_Id(Long categoryId, Pageable pageable);

    @Query(
            """
        select new kg.attractor.job_search_java_25.dto.VacancyDto(
           v.id,
           v.name,
           v.description,
           v.category.id,
           v.salary,
           v.expFrom,
           v.expTo,
           v.isActive,
           v.author.id,
           v.createdDate,
           v.updateTime
           ) from Vacancy v where v.author.id = :employerId
"""
    )
    List<VacancyDto> findAllByAuthorId(@Param("employerId") Long employerId);

    Page<Vacancy> findAllByAuthor_IdAndCategory_Id(Long authorId, Long categoryId, Pageable pageable);

}
