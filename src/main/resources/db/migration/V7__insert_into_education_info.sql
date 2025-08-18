INSERT INTO education_info (resume_id, institution, program, start_date, end_date, degree)
SELECT
    r.id                                                                                  AS resume_id,
    CASE MOD(r.id, 5)
        WHEN 0 THEN 'Кыргызский государственный университет'
        WHEN 1 THEN 'КГТУ'
        WHEN 2 THEN 'МГУ'
        WHEN 3 THEN 'СПбГУ'
        ELSE         'Назарбаев Университет'
        END                                                                                   AS institution,
    CASE MOD(r.id, 5)
        WHEN 0 THEN 'Информатика и вычислительная техника'
        WHEN 1 THEN 'Строительное дело'
        WHEN 2 THEN 'Прикладная математика и информатика'
        WHEN 3 THEN 'Филология'
        ELSE         'Финансы и кредит'
        END                                                                                   AS program,
    DATEADD('YEAR', - (6 + MOD(r.id, 6)), DATE '2020-09-01')                              AS start_date,
    DATEADD('YEAR', - (2 + MOD(r.id, 6)), DATE '2020-06-30')                              AS end_date,
    'Bachelor'                                                                            AS degree
FROM resumes r;

INSERT INTO education_info (resume_id, institution, program, start_date, end_date, degree)
SELECT
    r.id,
    CASE MOD(r.id, 4)
        WHEN 0 THEN 'СПбГУ'
        WHEN 1 THEN 'МГУ'
        WHEN 2 THEN 'Кыргызский государственный университет'
        ELSE         'Назарбаев Университет'
        END                                                                                   AS institution,
    CASE MOD(r.id, 4)
        WHEN 0 THEN 'Компьютерные науки'
        WHEN 1 THEN 'Экономика и финансы'
        WHEN 2 THEN 'Прикладная математика'
        ELSE         'Педагогика'
        END                                                                                   AS program,
    DATEADD('YEAR', - (4 + MOD(r.id, 5)), DATE '2021-09-01')                              AS start_date,
    DATEADD('YEAR', - (2 + MOD(r.id, 5)), DATE '2021-06-30')                              AS end_date,
    CASE WHEN MOD(r.id, 2) = 0 THEN 'Master' ELSE 'Certificate' END                       AS degree
FROM resumes r
WHERE MOD(r.id, 3) = 0;

INSERT INTO education_info (resume_id, institution, program, start_date, end_date, degree)
SELECT
    r.id,
    CASE MOD(r.id, 3)
        WHEN 0 THEN 'Bishkek IT Academy'
        WHEN 1 THEN 'Coursera'
        ELSE         'Udemy'
        END                                                                                   AS institution,
    CASE MOD(r.id, 3)
        WHEN 0 THEN 'Fullstack Development'
        WHEN 1 THEN 'Machine Learning by Stanford'
        ELSE         'Java Programming Masterclass'
        END                                                                                   AS program,
    DATEADD('YEAR', - (2 + MOD(r.id, 3)), DATE '2022-01-15')                              AS start_date,
    DATEADD('MONTH', 6 + MOD(r.id, 6), DATEADD('YEAR', - (2 + MOD(r.id, 3)), DATE '2022-01-15')) AS end_date,
    CASE MOD(r.id, 3)
        WHEN 0 THEN 'Certificate'
        WHEN 1 THEN 'Certificate'
        ELSE         'Diploma'
        END                                                                                   AS degree
FROM resumes r
WHERE MOD(r.id, 5) = 0;
