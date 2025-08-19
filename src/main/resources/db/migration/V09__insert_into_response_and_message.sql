
INSERT INTO responded_applicants (resume_id, vacancy_id, confirmation)
WITH ranked_pairs AS (
    SELECT
        r.id AS resume_id,
        v.id AS vacancy_id,
        ROW_NUMBER() OVER (
            PARTITION BY r.id
            ORDER BY COALESCE(v.created_date, CURRENT_TIMESTAMP) DESC, v.id
            ) AS rn
    FROM resumes r
             JOIN vacancies v
                  ON v.category_id = r.category_id
                      AND COALESCE(v.is_active, TRUE) = TRUE
)
SELECT
    rp.resume_id,
    rp.vacancy_id,
    CASE WHEN MOD(rp.resume_id + rp.vacancy_id, 2) = 0 THEN TRUE ELSE FALSE END AS confirmation
FROM ranked_pairs rp
WHERE rp.rn <= 2;


INSERT INTO messages (responded_applicant_id, content)
SELECT
    ra.id,
    CASE
        WHEN ra.confirmation = TRUE
            THEN 'Здравствуйте! Отклик принят. Давайте согласуем время звонка.'
        ELSE
            'Спасибо за отклик! Мы изучим резюме и вернёмся с обратной связью.'
        END
FROM responded_applicants ra;


INSERT INTO messages (responded_applicant_id, content)
SELECT
    ra.id,
    'Напоминаем: укажите удобные слоты для интервью (30–45 мин).'
FROM responded_applicants ra
WHERE MOD(ra.id, 2) = 0;
