
-- =====================================================================
-- Password (bcrypt, 10 rounds) for all non-admin users and admin: 'password'
-- $2a$10$7EqJtq98hPqEX7fNZaFWoO5.P8Y1S8S8p1ZC/7ju8hZ9erjRz.b.u
-- =====================================================================

BEGIN TRANSACTION;

-- DELETE FROM messages;
-- DELETE FROM responded_applicants;
-- DELETE FROM work_experience_info;
-- DELETE FROM education_info;
-- DELETE FROM contacts_info;
-- DELETE FROM vacancies;
-- DELETE FROM resumes;
-- DELETE FROM contact_type;
-- DELETE FROM categories;
-- DELETE FROM users;

-- ---------------------------------------------------------------------
-- CONTACT TYPES (IDs 1..5)
-- ---------------------------------------------------------------------
INSERT INTO contact_type (id, type) VALUES
                                        (1, 'PHONE'),
                                        (2, 'EMAIL'),
                                        (3, 'TELEGRAM'),
                                        (4, 'FACEBOOK'),
                                        (5, 'LINKEDIN');

-- ---------------------------------------------------------------------
-- USERS (IDs 1..7)
-- 1..3 APPLICANT, 4..6 EMPLOYER, 7 ADMIN
-- ---------------------------------------------------------------------
INSERT INTO users (id, name, surname, age, email, password, phone_number, avatar, account_type, enabled) VALUES
                                                                                                             (1, 'Алтынбек','Султанкулов',27,'altynbek@example.com','$2a$10$7EqJtq98hPqEX7fNZaFWoO5.P8Y1S8S8p1ZC/7ju8hZ9erjRz.b.u','+996700000101',NULL,'APPLICANT',TRUE),
                                                                                                             (2, 'Аида','Токтобекова',24,'aida.tokto@example.com','$2a$10$7EqJtq98hPqEX7fNZaFWoO5.P8Y1S8S8p1ZC/7ju8hZ9erjRz.b.u','+996700000102',NULL,'APPLICANT',TRUE),
                                                                                                             (3, 'Руслан','Маматов',31,'ruslan.m@example.com','$2a$10$7EqJtq98hPqEX7fNZaFWoO5.P8Y1S8S8p1ZC/7ju8hZ9erjRz.b.u','+996700000103',NULL,'APPLICANT',TRUE),
                                                                                                             (4, 'NomadTech',NULL,NULL,'hr@nomadtech.kg','$2a$10$7EqJtq98hPqEX7fNZaFWoO5.P8Y1S8S8p1ZC/7ju8hZ9erjRz.b.u','+996700000201',NULL,'EMPLOYER',TRUE),
                                                                                                             (5, 'KyrgyzSoft',NULL,NULL,'jobs@kyrgyzsoft.kg','$2a$10$7EqJtq98hPqEX7fNZaFWoO5.P8Y1S8S8p1ZC/7ju8hZ9erjRz.b.u','+996700000202',NULL,'EMPLOYER',TRUE),
                                                                                                             (6, 'BishkekDigital',NULL,NULL,'talent@bishkekdigital.kg','$2a$10$7EqJtq98hPqEX7fNZaFWoO5.P8Y1S8S8p1ZC/7ju8hZ9erjRz.b.u','+996700000203',NULL,'EMPLOYER',TRUE),
                                                                                                             (7, 'Admin','System',NULL,'admin@jobsearch.local','$2a$10$7EqJtq98hPqEX7fNZaFWoO5.P8Y1S8S8p1ZC/7ju8hZ9erjRz.b.u','+996700000001',NULL,'ADMIN',TRUE);

-- ---------------------------------------------------------------------
-- CATEGORIES (IDs 1..10)
-- ---------------------------------------------------------------------
INSERT INTO categories (id, name, parent_id) VALUES
                                                 (1, 'Информационные технологии', NULL),
                                                 (2, 'Продажи', NULL),
                                                 (3, 'Маркетинг', NULL),
                                                 (4, 'Дизайн', NULL),
                                                 (5, 'Финансы', NULL),
                                                 (6, 'Образование', NULL),
                                                 (7, 'Здравоохранение', NULL),
                                                 (8, 'Строительство', NULL),
                                                 (9, 'Логистика', NULL),
                                                 (10, 'Поддержка клиентов', NULL);

-- ---------------------------------------------------------------------
-- RESUMES (IDs 1..12)
-- Note: is_active in your YAML is BIGINT; we use 1/0
-- ---------------------------------------------------------------------
INSERT INTO resumes (id, applicant_id, name, category_id, salary, is_active, created_date, update_time) VALUES
                                                                                                            (1, 1, 'Java Backend Разработчик',              1, 120000, 1, TIMESTAMP '2025-07-15 10:00:00', TIMESTAMP '2025-08-06 09:10:00'),
                                                                                                            (2, 1, 'SQL/ETL Инженер',                       1, 100000, 1, TIMESTAMP '2025-06-20 12:00:00', TIMESTAMP '2025-08-03 14:45:00'),
                                                                                                            (3, 1, 'Системный аналитик',                    1, 110000, 0, TIMESTAMP '2025-05-10 09:00:00', TIMESTAMP '2025-06-01 18:10:00'),
                                                                                                            (4, 1, 'Саппорт инженер (L2)',                 10,  80000, 1, TIMESTAMP '2025-07-28 15:30:00', TIMESTAMP '2025-08-07 08:00:00'),

                                                                                                            (5, 2, 'Digital-маркетолог',                    3,  90000, 1, TIMESTAMP '2025-07-10 11:30:00', TIMESTAMP '2025-08-05 16:20:00'),
                                                                                                            (6, 2, 'Контент-менеджер',                      3,  70000, 1, TIMESTAMP '2025-06-05 10:25:00', TIMESTAMP '2025-07-29 12:05:00'),
                                                                                                            (7, 2, 'Графический дизайнер',                  4,  85000, 0, TIMESTAMP '2025-05-25 13:05:00', TIMESTAMP '2025-06-26 09:55:00'),
                                                                                                            (8, 2, 'Преподаватель IT курсов',               6,  60000, 1, TIMESTAMP '2025-07-30 08:50:00', TIMESTAMP '2025-08-07 09:05:00'),

                                                                                                            (9, 3, 'Финансовый аналитик',                   5, 130000, 1, TIMESTAMP '2025-07-01 09:40:00', TIMESTAMP '2025-08-01 17:40:00'),
                                                                                                            (10,3, 'Менеджер по продажам B2B',              2,  95000, 1, TIMESTAMP '2025-06-12 08:15:00', TIMESTAMP '2025-07-31 10:00:00'),
                                                                                                            (11,3, 'Логист (международные перевозки)',      9,  90000, 1, TIMESTAMP '2025-07-22 12:20:00', TIMESTAMP '2025-08-06 13:15:00'),
                                                                                                            (12,3, 'Прораб/инженер ПТО',                    8, 140000, 0, TIMESTAMP '2025-05-18 17:10:00', TIMESTAMP '2025-06-05 11:00:00');

-- ---------------------------------------------------------------------
-- CONTACTS_INFO (IDs 1..??)
-- ---------------------------------------------------------------------
INSERT INTO contacts_info (id, type_id, resume_id, contact_value) VALUES
                                                                      (1,  1, 1, '+996700000101'),
                                                                      (2,  2, 1, 'altynbek@example.com'),
                                                                      (3,  3, 1, '@altyn_java'),
                                                                      (4,  1, 2, '+996700000101'),
                                                                      (5,  2, 2, 'altynbek+etl@example.com'),
                                                                      (6,  1, 3, '+996700000101'),
                                                                      (7,  1, 4, '+996700000101'),
                                                                      (8,  3, 4, '@support_alt'),

                                                                      (9,  1, 5, '+996700000102'),
                                                                      (10, 2, 5, 'aida.tokto@example.com'),
                                                                      (11, 5, 5, 'https://linkedin.com/in/aida-tokto'),
                                                                      (12, 1, 6, '+996700000102'),
                                                                      (13, 1, 7, '+996700000102'),
                                                                      (14, 4, 7, 'https://facebook.com/aida.design'),
                                                                      (15, 2, 8, 'teach.aida@example.com'),

                                                                      (16, 1, 9, '+996700000103'),
                                                                      (17, 2, 9, 'ruslan.m@example.com'),
                                                                      (18, 1,10, '+996700000103'),
                                                                      (19, 1,11, '+996700000103'),
                                                                      (20, 3,11, '@ruslog'),
                                                                      (21, 1,12, '+996700000103');

-- ---------------------------------------------------------------------
-- EDUCATION_INFO (IDs 1..4)
-- ---------------------------------------------------------------------
INSERT INTO education_info (id, resume_id, institution, program, start_date, end_date, degree) VALUES
                                                                                                   (1, 1, 'КГТУ',  'Информатика и вычислительная техника', DATE '2017-09-01', DATE '2021-06-30', 'Бакалавр'),
                                                                                                   (2, 5, 'КРСУ',  'Маркетинг',                            DATE '2018-09-01', DATE '2022-06-30', 'Бакалавр'),
                                                                                                   (3, 9, 'АУЦА',  'Финансы',                              DATE '2014-09-01', DATE '2018-06-30', 'Бакалавр'),
                                                                                                   (4, 8, 'Coursera/EPAM', 'Java Fundamentals (сертификат)', DATE '2024-01-10', DATE '2024-05-10', 'Сертификат');

-- ---------------------------------------------------------------------
-- WORK_EXPERIENCE_INFO (IDs 1..6)
-- ---------------------------------------------------------------------
INSERT INTO work_experience_info (id, resume_id, years, company_name, position, responsibilities) VALUES
                                                                                                      (1, 1, 2, 'KyrgyzSoft',   'Java Developer',        'Spring Boot, REST, JDBC'),
                                                                                                      (2, 2, 1, 'NomadTech',    'ETL Engineer',          'ETL пайплайны, SQL'),
                                                                                                      (3, 5, 2, 'BishkekDigital','SMM/Performance',      'Meta/Google Ads, аналитика'),
                                                                                                      (4, 7, 3, 'Freelance',    'Graphic Designer',      'Брендинг, макеты'),
                                                                                                      (5, 9, 4, 'FinancePlus',  'Analyst',               'Финмодели, отчеты'),
                                                                                                      (6,11, 3, 'GlobalLog',    'Logistics Specialist',  'Маршруты, таможня');

-- ---------------------------------------------------------------------
-- VACANCIES (IDs 1..6)
-- ---------------------------------------------------------------------
INSERT INTO vacancies (id, name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time) VALUES
                                                                                                                                          (1, 'Java Backend Developer',
                                                                                                                                           'Разработка микросервисов: Spring Boot, REST, PostgreSQL. Работа в продуктовой команде.',
                                                                                                                                           1, 150000, 2, 5, TRUE, 5, TIMESTAMP '2025-08-01 10:00:00', TIMESTAMP '2025-08-06 09:00:00'),
                                                                                                                                          (2, 'Маркетолог (Performance)',
                                                                                                                                           'Запуск и оптимизация рекламных кампаний, аналитика, бюджетирование.',
                                                                                                                                           3, 110000, 1, 4, TRUE, 4, TIMESTAMP '2025-07-25 11:15:00', TIMESTAMP '2025-08-05 15:00:00'),
                                                                                                                                          (3, 'Графический дизайнер',
                                                                                                                                           'Баннеры, лендинги, брендбуки. Опыт в Figma/Adobe.',
                                                                                                                                           4, 90000, 1, 3, TRUE, 6, TIMESTAMP '2025-07-20 09:30:00', TIMESTAMP '2025-08-04 10:30:00'),
                                                                                                                                          (4, 'Финансовый аналитик',
                                                                                                                                           'Финансовое моделирование, отчётность, KPI. Excel/Power BI.',
                                                                                                                                           5, 160000, 3, 6, TRUE, 5, TIMESTAMP '2025-07-29 13:40:00', TIMESTAMP '2025-08-03 14:00:00'),
                                                                                                                                          (5, 'Менеджер по продажам (B2B)',
                                                                                                                                           'Активные продажи, ведение воронки, сделки и тендеры.',
                                                                                                                                           2, 100000, 1, 4, TRUE, 4, TIMESTAMP '2025-07-18 12:10:00', TIMESTAMP '2025-08-02 16:20:00'),
                                                                                                                                          (6, 'Логист-координатор',
                                                                                                                                           'Планирование маршрутов, работа с перевозчиками, таможня.',
                                                                                                                                           9, 95000, 2, 5, TRUE, 6, TIMESTAMP '2025-07-27 08:50:00', TIMESTAMP '2025-08-06 12:25:00');

-- ---------------------------------------------------------------------
-- RESPONDED_APPLICANTS (IDs 1..4)
-- Leave vacancies 3 ('Графический дизайнер') and 5 ('Менеджер по продажам (B2B)') without responses
-- ---------------------------------------------------------------------
INSERT INTO responded_applicants (id, resume_id, vacancy_id, confirmation) VALUES
                                                                               (1, 1, 1, FALSE),   -- Алтынбек -> Java Backend Developer
                                                                               (2, 5, 2, FALSE),   -- Аида -> Маркетолог (Performance)
                                                                               (3, 9, 4, TRUE),    -- Руслан -> Финансовый аналитик
                                                                               (4,11, 6, FALSE);   -- Руслан -> Логист-координатор

-- ---------------------------------------------------------------------
-- MESSAGES (IDs 1..4)  (column name is "responded_applicants")
-- ---------------------------------------------------------------------
INSERT INTO messages (id, responded_applicants, content, timestamp) VALUES
                                                                        (1, 1, 'Здравствуйте! Готов пройти техническое интервью.', CURRENT_TIMESTAMP()),
                                                                        (2, 1, 'Привет! Пришлите, пожалуйста, GitHub и время для созвона.', CURRENT_TIMESTAMP()),
                                                                        (3, 2, 'Здравствуйте! Есть кейсы по снижению CPL на 35%.', CURRENT_TIMESTAMP()),
                                                                        (4, 3, 'Подтверждаю участие. Когда удобно созвониться?', CURRENT_TIMESTAMP());

-- ---------------------------------------------------------------------
-- Bump identity sequences to avoid future collisions (H2)
-- ---------------------------------------------------------------------
ALTER TABLE contact_type           ALTER COLUMN id RESTART WITH 6;
ALTER TABLE users                  ALTER COLUMN id RESTART WITH 8;
ALTER TABLE categories             ALTER COLUMN id RESTART WITH 11;
ALTER TABLE resumes                ALTER COLUMN id RESTART WITH 13;
ALTER TABLE contacts_info          ALTER COLUMN id RESTART WITH 22;
ALTER TABLE education_info         ALTER COLUMN id RESTART WITH 5;
ALTER TABLE work_experience_info   ALTER COLUMN id RESTART WITH 7;
ALTER TABLE vacancies              ALTER COLUMN id RESTART WITH 7;
ALTER TABLE responded_applicants   ALTER COLUMN id RESTART WITH 5;
ALTER TABLE messages               ALTER COLUMN id RESTART WITH 5;

COMMIT;
