package kg.attractor.job_search_java_25.repository;


import kg.attractor.job_search_java_25.dto.vacancyDtos.VacancyListItemDto;
import kg.attractor.job_search_java_25.model.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;


@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {

    Page<Vacancy> findByCategory_Id(Long categoryId, Pageable pageable);

    Page<Vacancy> findByAuthor_IdAndCategory_Id(Long authorId, Long categoryId, Pageable pageable);

    @Query("""
              select v
              from Vacancy v
              left join v.category c
              where (:categoryId is null or c.id = :categoryId)
                and (:activeOnly is null or (:activeOnly = true and v.isActive = true) or (:activeOnly = false))
                and (:minSalary is null or v.salary >= :minSalary)
                and (:maxSalary is null or v.salary <= :maxSalary)
                and (:expFrom   is null or v.expFrom >= :expFrom)
                and (:expTo     is null or v.expTo   <= :expTo)
                and (
                    :term is null or :term = '' or
                    lower(v.name) like lower(concat('%', :term, '%')) or
                    lower(v.description) like lower(concat('%', :term, '%')) or
                    lower(c.name) like lower(concat('%', :term, '%'))
                )
            """)
    Page<Vacancy> search(@Param("categoryId") Long categoryId,
                         @Param("activeOnly") Boolean activeOnly,
                         @Param("minSalary") Float minSalary,
                         @Param("maxSalary") Float maxSalary,
                         @Param("expFrom") Integer expFrom,
                         @Param("expTo") Integer expTo,
                         @Param("term") String term,
                         Pageable pageable);

    @Query("select v.author.id from Vacancy v where v.id = :vacancyId")
    Long getOwnerId(@Param("vacancyId") Long vacancyId);

    @Modifying
    @Transactional
    @Query("update Vacancy v set v.updateTime = CURRENT_TIMESTAMP where v.id = :id")
    int touch(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("update Vacancy v set v.isActive = :isActive where v.id = :id")
    int setActive(@Param("id") Long id, @Param("isActive") Boolean isActive);

    @Query("""
              select new kg.attractor.job_search_java_25.dto.vacancyDtos.VacancyListItemDto(
                v.id, v.name, c.name, v.salary, v.isActive, v.updateTime
              )
              from Vacancy v
              left join v.category c
              where (:authorId is null or v.author.id = :authorId)
              order by v.updateTime desc
            """)
    Page<VacancyListItemDto> findList(@Param("authorId") Long authorId, Pageable pageable);

    Optional<Vacancy> findById(Long id);

    @Query("""
            select new kg.attractor.job_search_java_25.dto.vacancyDtos.VacancyListItemDto(
                v.id, v.name, v.category.name, v.salary, v.isActive, v.updateTime
            )
            from Vacancy v
            where (:salaryFrom is null or v.salary >= :salaryFrom)
              and (:salaryTo is null or v.salary <= :salaryTo)
            """)
    Page<VacancyListItemDto> findListWithSalary(
            @Param("salaryFrom") BigDecimal salaryFrom,
            @Param("salaryTo") BigDecimal salaryTo,
            Pageable pageable);

    @Query("""
            select new kg.attractor.job_search_java_25.dto.vacancyDtos.VacancyListItemDto(
                v.id, v.name, v.category.name, v.salary, v.isActive, v.updateTime
            )
            from Vacancy v
            where v.category.id = :categoryId
              and (:salaryFrom is null or v.salary >= :salaryFrom)
              and (:salaryTo is null or v.salary <= :salaryTo)
            """)
    Page<VacancyListItemDto> findByCategory_IdAndSalary(
            @Param("categoryId") Long categoryId,
            @Param("salaryFrom") BigDecimal salaryFrom,
            @Param("salaryTo") BigDecimal salaryTo,
            Pageable pageable);
}
