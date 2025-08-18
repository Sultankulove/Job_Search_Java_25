CREATE SEQUENCE IF NOT EXISTS work_exp_seq START WITH 1 INCREMENT BY 1;

-- 1) Для каждого резюме
INSERT INTO work_experience_info (id, resume_id, years, company_name, position, responsibilities)
SELECT
    NEXT VALUE FOR work_exp_seq,
    r.id,
    1 + MOD(r.id, 7),
    CASE MOD(r.id, 10)
        WHEN 0 THEN 'EPAM Systems' WHEN 1 THEN 'Luxoft' WHEN 2 THEN 'Google' WHEN 3 THEN 'Microsoft'
        WHEN 4 THEN 'КыргызТелеком' WHEN 5 THEN 'AstanaBuild' WHEN 6 THEN 'MedService'
        WHEN 7 THEN 'Школа №45 г. Бишкек' WHEN 8 THEN 'Университет КГУ' ELSE 'StartupHub' END,
    CASE MOD(r.id, 10)
        WHEN 0 THEN 'Java Developer' WHEN 1 THEN 'Backend Engineer' WHEN 2 THEN 'Data Analyst'
        WHEN 3 THEN 'DevOps Engineer' WHEN 4 THEN 'Network Engineer' WHEN 5 THEN 'Проектный инженер'
        WHEN 6 THEN 'Терапевт' WHEN 7 THEN 'Учитель математики' WHEN 8 THEN 'Преподаватель'
        ELSE 'SMM-специалист' END,
    CASE MOD(r.id, 10)
        WHEN 0 THEN 'Разработка микросервисов' WHEN 1 THEN 'Поддержка корпоративных приложений'
        WHEN 2 THEN 'Аналитика и отчётность' WHEN 3 THEN 'CI/CD и облако' WHEN 4 THEN 'Эксплуатация сетей'
        WHEN 5 THEN 'Контроль проектной документации' WHEN 6 THEN 'Приём пациентов'
        WHEN 7 THEN 'Преподавание алгебры' WHEN 8 THEN 'Лекции и семинары' ELSE 'Контент и продвижение' END
FROM resumes r
         JOIN users u ON u.id = r.applicant_id;

INSERT INTO work_experience_info (id, resume_id, years, company_name, position, responsibilities)
SELECT
    NEXT VALUE FOR work_exp_seq,
    r.id,
    1 + MOD(r.id + 2, 5),
    CASE MOD(r.id + 3, 8)
        WHEN 0 THEN 'EPAM Systems' WHEN 1 THEN 'Luxoft' WHEN 2 THEN 'Google' WHEN 3 THEN 'Microsoft'
        WHEN 4 THEN 'Yandex' WHEN 5 THEN 'Mail.ru Group' WHEN 6 THEN 'Khan Academy' ELSE 'StartupHub' END,
    CASE MOD(r.id + 1, 8)
        WHEN 0 THEN 'Python Developer' WHEN 1 THEN 'QA Engineer' WHEN 2 THEN 'ML Engineer'
        WHEN 3 THEN 'Frontend Developer' WHEN 4 THEN 'Product Designer' WHEN 5 THEN 'Финансовый аналитик'
        WHEN 6 THEN 'Юрист' ELSE 'Логист' END,
    CASE MOD(r.id + 4, 8)
        WHEN 0 THEN 'REST API и интеграции' WHEN 1 THEN 'Автотесты и регресс'
        WHEN 2 THEN 'Модели и эксперименты' WHEN 3 THEN 'UI разработка' WHEN 4 THEN 'Прототипирование'
        WHEN 5 THEN 'Финмодели и отчёты' WHEN 6 THEN 'Договоры и консультации' ELSE 'Снабжение и поставки' END
FROM resumes r
         JOIN users u ON u.id = r.applicant_id
WHERE MOD(r.id, 3) = 0;

INSERT INTO work_experience_info (id, resume_id, years, company_name, position, responsibilities)
SELECT
    NEXT VALUE FOR work_exp_seq,
    r.id,
    1 + MOD(r.id + 1, 4),
    CASE MOD(r.id + 5, 6)
        WHEN 0 THEN 'Amazon' WHEN 1 THEN 'Meta' WHEN 2 THEN 'Oracle'
        WHEN 3 THEN 'КыргызТелеком' WHEN 4 THEN 'AstanaBuild' ELSE 'MedService' END,
    CASE MOD(r.id + 2, 6)
        WHEN 0 THEN 'Cloud Engineer' WHEN 1 THEN 'Android Developer' WHEN 2 THEN 'DBA'
        WHEN 3 THEN 'Инженер ПТО' WHEN 4 THEN 'Архитектор' ELSE 'Врач общей практики' END,
    CASE MOD(r.id + 3, 6)
        WHEN 0 THEN 'Облачная инфраструктура' WHEN 1 THEN 'Мобильная разработка' WHEN 2 THEN 'Администрирование БД'
        WHEN 3 THEN 'Сметы и технадзор' WHEN 4 THEN 'Проектирование зданий' ELSE 'Амбулаторный приём' END
FROM resumes r
         JOIN users u ON u.id = r.applicant_id
WHERE MOD(r.id, 5) = 0;
