-- INSERTS for vacancies table

INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'ML Engineer',
    'Продакшн-развертывание ML решений.',
    (SELECT id FROM categories WHERE name = 'Data Science' LIMIT 1),
    1304,
    1,
    3,
    true,
    (SELECT id FROM users WHERE email = 'emp1@gmail.com' LIMIT 1),
    '2024-06-29',
    '2024-10-01'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'UI/UX Designer',
    'Разработка интерфейсов, проведение юзабилити-тестов.',
    (SELECT id FROM categories WHERE name = 'UI/UX дизайн' LIMIT 1),
    3171,
    0,
    1,
    false,
    (SELECT id FROM users WHERE email = 'emp1@gmail.com' LIMIT 1),
    '2024-11-13',
    '2024-12-03'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Angular Developer',
    'Разработка корпоративных интерфейсов на Angular.',
    (SELECT id FROM categories WHERE name = 'Frontend разработка' LIMIT 1),
    1633,
    0,
    5,
    false,
    (SELECT id FROM users WHERE email = 'emp1@gmail.com' LIMIT 1),
    '2025-06-11',
    '2025-07-26'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Cloud Engineer',
    'Поддержка облачной инфраструктуры (AWS, GCP).',
    (SELECT id FROM categories WHERE name = 'DevOps' LIMIT 1),
    4436,
    3,
    5,
    true,
    (SELECT id FROM users WHERE email = 'emp1@gmail.com' LIMIT 1),
    '2025-02-26',
    '2025-05-08'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Репетитор по математике',
    'Индивидуальные занятия по алгебре и геометрии.',
    (SELECT id FROM categories WHERE name = 'Учитель математики' LIMIT 1),
    2518,
    1,
    5,
    true,
    (SELECT id FROM users WHERE email = 'emp1@gmail.com' LIMIT 1),
    '2025-03-06',
    '2025-03-23'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Семейный врач',
    'Ведение пациентов всех возрастов, профилактика заболеваний.',
    (SELECT id FROM categories WHERE name = 'Врач-терапевт' LIMIT 1),
    4353,
    3,
    7,
    true,
    (SELECT id FROM users WHERE email = 'emp1@gmail.com' LIMIT 1),
    '2025-05-05',
    '2025-06-11'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Бухгалтер',
    'Ведение бухгалтерского учета, подготовка отчетности.',
    (SELECT id FROM categories WHERE name = 'Бухгалтер' LIMIT 1),
    1759,
    2,
    4,
    false,
    (SELECT id FROM users WHERE email = 'emp1@gmail.com' LIMIT 1),
    '2024-09-21',
    '2024-11-05'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Python Backend Developer',
    'Разработка REST API и интеграция с внешними сервисами.',
    (SELECT id FROM categories WHERE name = 'Backend разработка' LIMIT 1),
    3561,
    2,
    5,
    true,
    (SELECT id FROM users WHERE email = 'emp1@gmail.com' LIMIT 1),
    '2024-09-23',
    '2024-12-24'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Test Automation Engineer',
    'Разработка автотестов на Selenium и JUnit.',
    (SELECT id FROM categories WHERE name = 'QA' LIMIT 1),
    4453,
    3,
    6,
    true,
    (SELECT id FROM users WHERE email = 'emp1@gmail.com' LIMIT 1),
    '2024-08-28',
    '2024-11-23'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'SMM-специалист',
    'Продвижение бренда в соцсетях.',
    (SELECT id FROM categories WHERE name = 'Маркетолог' LIMIT 1),
    940,
    3,
    4,
    true,
    (SELECT id FROM users WHERE email = 'emp1@gmail.com' LIMIT 1),
    '2024-08-06',
    '2024-10-02'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Семейный врач',
    'Ведение пациентов всех возрастов, профилактика заболеваний.',
    (SELECT id FROM categories WHERE name = 'Врач-терапевт' LIMIT 1),
    2004,
    0,
    2,
    false,
    (SELECT id FROM users WHERE email = 'emp2@gmail.com' LIMIT 1),
    '2024-09-13',
    '2024-11-03'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Java Developer',
    'Разработка и поддержка backend-сервисов на Java/Spring.',
    (SELECT id FROM categories WHERE name = 'Backend разработка' LIMIT 1),
    2575,
    0,
    2,
    false,
    (SELECT id FROM users WHERE email = 'emp2@gmail.com' LIMIT 1),
    '2024-11-09',
    '2025-01-21'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Бухгалтер',
    'Ведение бухгалтерского учета, подготовка отчетности.',
    (SELECT id FROM categories WHERE name = 'Бухгалтер' LIMIT 1),
    2080,
    2,
    3,
    false,
    (SELECT id FROM users WHERE email = 'emp2@gmail.com' LIMIT 1),
    '2024-08-30',
    '2024-11-11'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'UI/UX Designer',
    'Разработка интерфейсов, проведение юзабилити-тестов.',
    (SELECT id FROM categories WHERE name = 'UI/UX дизайн' LIMIT 1),
    2449,
    1,
    4,
    false,
    (SELECT id FROM users WHERE email = 'emp2@gmail.com' LIMIT 1),
    '2024-11-15',
    '2025-02-07'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Data Scientist',
    'Разработка ML моделей, анализ данных.',
    (SELECT id FROM categories WHERE name = 'Data Science' LIMIT 1),
    1472,
    3,
    7,
    true,
    (SELECT id FROM users WHERE email = 'emp2@gmail.com' LIMIT 1),
    '2025-05-08',
    '2025-07-18'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'SMM-специалист',
    'Продвижение бренда в соцсетях.',
    (SELECT id FROM categories WHERE name = 'Маркетолог' LIMIT 1),
    1935,
    2,
    6,
    false,
    (SELECT id FROM users WHERE email = 'emp2@gmail.com' LIMIT 1),
    '2024-10-16',
    '2024-12-04'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'ML Engineer',
    'Продакшн-развертывание ML решений.',
    (SELECT id FROM categories WHERE name = 'Data Science' LIMIT 1),
    3760,
    0,
    2,
    false,
    (SELECT id FROM users WHERE email = 'emp3@gmail.com' LIMIT 1),
    '2024-09-07',
    '2024-10-07'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Репетитор по математике',
    'Индивидуальные занятия по алгебре и геометрии.',
    (SELECT id FROM categories WHERE name = 'Учитель математики' LIMIT 1),
    1194,
    0,
    2,
    true,
    (SELECT id FROM users WHERE email = 'emp3@gmail.com' LIMIT 1),
    '2024-11-07',
    '2024-11-26'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Test Automation Engineer',
    'Разработка автотестов на Selenium и JUnit.',
    (SELECT id FROM categories WHERE name = 'QA' LIMIT 1),
    3406,
    2,
    5,
    true,
    (SELECT id FROM users WHERE email = 'emp3@gmail.com' LIMIT 1),
    '2025-03-24',
    '2025-05-10'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Frontend Developer',
    'Разработка SPA приложений на React/Vue.',
    (SELECT id FROM categories WHERE name = 'Frontend разработка' LIMIT 1),
    4540,
    0,
    1,
    false,
    (SELECT id FROM users WHERE email = 'emp3@gmail.com' LIMIT 1),
    '2024-06-20',
    '2024-09-15'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Маркетолог',
    'Разработка и реализация маркетинговых стратегий.',
    (SELECT id FROM categories WHERE name = 'Маркетолог' LIMIT 1),
    4254,
    1,
    5,
    true,
    (SELECT id FROM users WHERE email = 'emp3@gmail.com' LIMIT 1),
    '2025-03-23',
    '2025-06-28'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Cloud Engineer',
    'Поддержка облачной инфраструктуры (AWS, GCP).',
    (SELECT id FROM categories WHERE name = 'DevOps' LIMIT 1),
    2408,
    0,
    3,
    false,
    (SELECT id FROM users WHERE email = 'emp3@gmail.com' LIMIT 1),
    '2024-12-03',
    '2024-12-24'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Product Designer',
    'Участие в проектировании продуктов, дизайн-макеты.',
    (SELECT id FROM categories WHERE name = 'UI/UX дизайн' LIMIT 1),
    4253,
    0,
    5,
    true,
    (SELECT id FROM users WHERE email = 'emp3@gmail.com' LIMIT 1),
    '2025-01-16',
    '2025-04-13'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'ML Engineer',
    'Продакшн-развертывание ML решений.',
    (SELECT id FROM categories WHERE name = 'Data Science' LIMIT 1),
    1852,
    1,
    2,
    false,
    (SELECT id FROM users WHERE email = 'emp4@gmail.com' LIMIT 1),
    '2024-07-05',
    '2024-09-11'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Test Automation Engineer',
    'Разработка автотестов на Selenium и JUnit.',
    (SELECT id FROM categories WHERE name = 'QA' LIMIT 1),
    2206,
    1,
    3,
    false,
    (SELECT id FROM users WHERE email = 'emp4@gmail.com' LIMIT 1),
    '2024-10-05',
    '2024-12-20'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Java Developer',
    'Разработка и поддержка backend-сервисов на Java/Spring.',
    (SELECT id FROM categories WHERE name = 'Backend разработка' LIMIT 1),
    2898,
    1,
    3,
    false,
    (SELECT id FROM users WHERE email = 'emp4@gmail.com' LIMIT 1),
    '2025-04-15',
    '2025-07-10'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Маркетолог',
    'Разработка и реализация маркетинговых стратегий.',
    (SELECT id FROM categories WHERE name = 'Маркетолог' LIMIT 1),
    764,
    3,
    6,
    false,
    (SELECT id FROM users WHERE email = 'emp4@gmail.com' LIMIT 1),
    '2024-10-09',
    '2024-10-19'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Angular Developer',
    'Разработка корпоративных интерфейсов на Angular.',
    (SELECT id FROM categories WHERE name = 'Frontend разработка' LIMIT 1),
    584,
    0,
    1,
    true,
    (SELECT id FROM users WHERE email = 'emp4@gmail.com' LIMIT 1),
    '2024-12-22',
    '2025-01-11'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'DevOps Engineer',
    'Настройка CI/CD, работа с Docker/Kubernetes.',
    (SELECT id FROM categories WHERE name = 'DevOps' LIMIT 1),
    880,
    0,
    3,
    false,
    (SELECT id FROM users WHERE email = 'emp4@gmail.com' LIMIT 1),
    '2025-05-08',
    '2025-05-27'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Терапевт',
    'Амбулаторный прием пациентов, диагностика, назначение лечения.',
    (SELECT id FROM categories WHERE name = 'Врач-терапевт' LIMIT 1),
    4626,
    3,
    7,
    false,
    (SELECT id FROM users WHERE email = 'emp4@gmail.com' LIMIT 1),
    '2025-01-27',
    '2025-04-25'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Репетитор по математике',
    'Индивидуальные занятия по алгебре и геометрии.',
    (SELECT id FROM categories WHERE name = 'Учитель математики' LIMIT 1),
    3390,
    2,
    5,
    true,
    (SELECT id FROM users WHERE email = 'emp4@gmail.com' LIMIT 1),
    '2025-01-01',
    '2025-01-13'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'ML Engineer',
    'Продакшн-развертывание ML решений.',
    (SELECT id FROM categories WHERE name = 'Data Science' LIMIT 1),
    4607,
    3,
    7,
    true,
    (SELECT id FROM users WHERE email = 'emp5@gmail.com' LIMIT 1),
    '2024-05-25',
    '2024-08-28'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Учитель математики',
    'Преподавание математики в старших классах.',
    (SELECT id FROM categories WHERE name = 'Учитель математики' LIMIT 1),
    1826,
    0,
    1,
    false,
    (SELECT id FROM users WHERE email = 'emp5@gmail.com' LIMIT 1),
    '2025-01-17',
    '2025-04-14'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'UI/UX Designer',
    'Разработка интерфейсов, проведение юзабилити-тестов.',
    (SELECT id FROM categories WHERE name = 'UI/UX дизайн' LIMIT 1),
    4808,
    3,
    4,
    true,
    (SELECT id FROM users WHERE email = 'emp5@gmail.com' LIMIT 1),
    '2024-07-22',
    '2024-10-06'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Главный бухгалтер',
    'Организация финансовой отчетности компании.',
    (SELECT id FROM categories WHERE name = 'Бухгалтер' LIMIT 1),
    2213,
    3,
    5,
    true,
    (SELECT id FROM users WHERE email = 'emp5@gmail.com' LIMIT 1),
    '2024-11-09',
    '2024-11-28'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Cloud Engineer',
    'Поддержка облачной инфраструктуры (AWS, GCP).',
    (SELECT id FROM categories WHERE name = 'DevOps' LIMIT 1),
    876,
    1,
    2,
    true,
    (SELECT id FROM users WHERE email = 'emp5@gmail.com' LIMIT 1),
    '2025-01-27',
    '2025-02-21'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'UI/UX Designer',
    'Разработка интерфейсов, проведение юзабилити-тестов.',
    (SELECT id FROM categories WHERE name = 'UI/UX дизайн' LIMIT 1),
    817,
    1,
    6,
    true,
    (SELECT id FROM users WHERE email = 'дмитрий6@company.com' LIMIT 1),
    '2024-11-27',
    '2025-02-13'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Маркетолог',
    'Разработка и реализация маркетинговых стратегий.',
    (SELECT id FROM categories WHERE name = 'Маркетолог' LIMIT 1),
    1738,
    2,
    4,
    true,
    (SELECT id FROM users WHERE email = 'дмитрий6@company.com' LIMIT 1),
    '2024-10-13',
    '2024-11-26'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Семейный врач',
    'Ведение пациентов всех возрастов, профилактика заболеваний.',
    (SELECT id FROM categories WHERE name = 'Врач-терапевт' LIMIT 1),
    4479,
    0,
    4,
    true,
    (SELECT id FROM users WHERE email = 'дмитрий6@company.com' LIMIT 1),
    '2025-06-13',
    '2025-06-24'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'DevOps Engineer',
    'Настройка CI/CD, работа с Docker/Kubernetes.',
    (SELECT id FROM categories WHERE name = 'DevOps' LIMIT 1),
    1059,
    1,
    6,
    false,
    (SELECT id FROM users WHERE email = 'дмитрий6@company.com' LIMIT 1),
    '2024-09-02',
    '2024-10-30'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Angular Developer',
    'Разработка корпоративных интерфейсов на Angular.',
    (SELECT id FROM categories WHERE name = 'Frontend разработка' LIMIT 1),
    3420,
    3,
    5,
    false,
    (SELECT id FROM users WHERE email = 'дмитрий6@company.com' LIMIT 1),
    '2025-06-24',
    '2025-08-11'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Test Automation Engineer',
    'Разработка автотестов на Selenium и JUnit.',
    (SELECT id FROM categories WHERE name = 'QA' LIMIT 1),
    2280,
    3,
    4,
    true,
    (SELECT id FROM users WHERE email = 'дмитрий6@company.com' LIMIT 1),
    '2024-07-10',
    '2024-09-21'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Java Developer',
    'Разработка и поддержка backend-сервисов на Java/Spring.',
    (SELECT id FROM categories WHERE name = 'Backend разработка' LIMIT 1),
    2253,
    2,
    6,
    false,
    (SELECT id FROM users WHERE email = 'дмитрий6@company.com' LIMIT 1),
    '2024-12-06',
    '2025-01-10'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Учитель математики',
    'Преподавание математики в старших классах.',
    (SELECT id FROM categories WHERE name = 'Учитель математики' LIMIT 1),
    4100,
    3,
    4,
    true,
    (SELECT id FROM users WHERE email = 'дмитрий6@company.com' LIMIT 1),
    '2025-02-23',
    '2025-03-16'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Frontend Developer',
    'Разработка SPA приложений на React/Vue.',
    (SELECT id FROM categories WHERE name = 'Frontend разработка' LIMIT 1),
    4411,
    3,
    8,
    true,
    (SELECT id FROM users WHERE email = 'жанат7@company.com' LIMIT 1),
    '2024-11-29',
    '2025-03-07'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'UI/UX Designer',
    'Разработка интерфейсов, проведение юзабилити-тестов.',
    (SELECT id FROM categories WHERE name = 'UI/UX дизайн' LIMIT 1),
    763,
    2,
    6,
    false,
    (SELECT id FROM users WHERE email = 'жанат7@company.com' LIMIT 1),
    '2025-04-09',
    '2025-07-03'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Бухгалтер',
    'Ведение бухгалтерского учета, подготовка отчетности.',
    (SELECT id FROM categories WHERE name = 'Бухгалтер' LIMIT 1),
    2855,
    2,
    5,
    false,
    (SELECT id FROM users WHERE email = 'жанат7@company.com' LIMIT 1),
    '2025-04-09',
    '2025-06-27'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Учитель математики',
    'Преподавание математики в старших классах.',
    (SELECT id FROM categories WHERE name = 'Учитель математики' LIMIT 1),
    2300,
    1,
    4,
    false,
    (SELECT id FROM users WHERE email = 'жанат7@company.com' LIMIT 1),
    '2025-05-31',
    '2025-07-10'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'SMM-специалист',
    'Продвижение бренда в соцсетях.',
    (SELECT id FROM categories WHERE name = 'Маркетолог' LIMIT 1),
    4806,
    1,
    6,
    true,
    (SELECT id FROM users WHERE email = 'жанат7@company.com' LIMIT 1),
    '2025-05-06',
    '2025-06-20'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Data Scientist',
    'Разработка ML моделей, анализ данных.',
    (SELECT id FROM categories WHERE name = 'Data Science' LIMIT 1),
    2911,
    1,
    6,
    true,
    (SELECT id FROM users WHERE email = 'жанат7@company.com' LIMIT 1),
    '2025-05-26',
    '2025-07-30'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Семейный врач',
    'Ведение пациентов всех возрастов, профилактика заболеваний.',
    (SELECT id FROM categories WHERE name = 'Врач-терапевт' LIMIT 1),
    3875,
    2,
    6,
    true,
    (SELECT id FROM users WHERE email = 'жанат7@company.com' LIMIT 1),
    '2025-05-30',
    '2025-08-08'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Cloud Engineer',
    'Поддержка облачной инфраструктуры (AWS, GCP).',
    (SELECT id FROM categories WHERE name = 'DevOps' LIMIT 1),
    4297,
    1,
    3,
    false,
    (SELECT id FROM users WHERE email = 'жанат7@company.com' LIMIT 1),
    '2024-08-17',
    '2024-09-05'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Cloud Engineer',
    'Поддержка облачной инфраструктуры (AWS, GCP).',
    (SELECT id FROM categories WHERE name = 'DevOps' LIMIT 1),
    596,
    3,
    8,
    false,
    (SELECT id FROM users WHERE email = 'виктория8@company.com' LIMIT 1),
    '2024-11-01',
    '2024-12-20'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Семейный врач',
    'Ведение пациентов всех возрастов, профилактика заболеваний.',
    (SELECT id FROM categories WHERE name = 'Врач-терапевт' LIMIT 1),
    1678,
    0,
    2,
    false,
    (SELECT id FROM users WHERE email = 'виктория8@company.com' LIMIT 1),
    '2024-10-21',
    '2025-01-27'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'QA Engineer',
    'Ручное и автоматизированное тестирование веб-приложений.',
    (SELECT id FROM categories WHERE name = 'QA' LIMIT 1),
    512,
    0,
    1,
    true,
    (SELECT id FROM users WHERE email = 'виктория8@company.com' LIMIT 1),
    '2024-09-28',
    '2024-11-12'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Учитель математики',
    'Преподавание математики в старших классах.',
    (SELECT id FROM categories WHERE name = 'Учитель математики' LIMIT 1),
    4688,
    3,
    6,
    false,
    (SELECT id FROM users WHERE email = 'виктория8@company.com' LIMIT 1),
    '2025-06-14',
    '2025-07-27'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'ML Engineer',
    'Продакшн-развертывание ML решений.',
    (SELECT id FROM categories WHERE name = 'Data Science' LIMIT 1),
    3064,
    1,
    2,
    false,
    (SELECT id FROM users WHERE email = 'виктория8@company.com' LIMIT 1),
    '2025-02-20',
    '2025-05-14'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Frontend Developer',
    'Разработка SPA приложений на React/Vue.',
    (SELECT id FROM categories WHERE name = 'Frontend разработка' LIMIT 1),
    4160,
    1,
    2,
    false,
    (SELECT id FROM users WHERE email = 'виктория8@company.com' LIMIT 1),
    '2024-06-04',
    '2024-08-23'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Главный бухгалтер',
    'Организация финансовой отчетности компании.',
    (SELECT id FROM categories WHERE name = 'Бухгалтер' LIMIT 1),
    1551,
    2,
    4,
    true,
    (SELECT id FROM users WHERE email = 'виктория8@company.com' LIMIT 1),
    '2024-06-06',
    '2024-09-06'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'UI/UX Designer',
    'Разработка интерфейсов, проведение юзабилити-тестов.',
    (SELECT id FROM categories WHERE name = 'UI/UX дизайн' LIMIT 1),
    1321,
    0,
    1,
    true,
    (SELECT id FROM users WHERE email = 'виктория8@company.com' LIMIT 1),
    '2024-05-24',
    '2024-08-26'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'SMM-специалист',
    'Продвижение бренда в соцсетях.',
    (SELECT id FROM categories WHERE name = 'Маркетолог' LIMIT 1),
    3490,
    2,
    3,
    true,
    (SELECT id FROM users WHERE email = 'виктория8@company.com' LIMIT 1),
    '2024-09-22',
    '2024-11-19'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Python Backend Developer',
    'Разработка REST API и интеграция с внешними сервисами.',
    (SELECT id FROM categories WHERE name = 'Backend разработка' LIMIT 1),
    1966,
    0,
    3,
    true,
    (SELECT id FROM users WHERE email = 'виктория8@company.com' LIMIT 1),
    '2024-12-23',
    '2025-01-30'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'SMM-специалист',
    'Продвижение бренда в соцсетях.',
    (SELECT id FROM categories WHERE name = 'Маркетолог' LIMIT 1),
    4547,
    2,
    3,
    true,
    (SELECT id FROM users WHERE email = 'андрей9@company.com' LIMIT 1),
    '2025-03-20',
    '2025-05-13'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Java Developer',
    'Разработка и поддержка backend-сервисов на Java/Spring.',
    (SELECT id FROM categories WHERE name = 'Backend разработка' LIMIT 1),
    787,
    1,
    5,
    true,
    (SELECT id FROM users WHERE email = 'андрей9@company.com' LIMIT 1),
    '2024-07-09',
    '2024-08-26'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Angular Developer',
    'Разработка корпоративных интерфейсов на Angular.',
    (SELECT id FROM categories WHERE name = 'Frontend разработка' LIMIT 1),
    1534,
    3,
    4,
    true,
    (SELECT id FROM users WHERE email = 'андрей9@company.com' LIMIT 1),
    '2024-10-02',
    '2024-12-17'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Cloud Engineer',
    'Поддержка облачной инфраструктуры (AWS, GCP).',
    (SELECT id FROM categories WHERE name = 'DevOps' LIMIT 1),
    3780,
    0,
    4,
    true,
    (SELECT id FROM users WHERE email = 'андрей9@company.com' LIMIT 1),
    '2024-07-29',
    '2024-10-26'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Учитель математики',
    'Преподавание математики в старших классах.',
    (SELECT id FROM categories WHERE name = 'Учитель математики' LIMIT 1),
    4810,
    1,
    2,
    false,
    (SELECT id FROM users WHERE email = 'андрей9@company.com' LIMIT 1),
    '2024-11-14',
    '2025-02-04'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Терапевт',
    'Амбулаторный прием пациентов, диагностика, назначение лечения.',
    (SELECT id FROM categories WHERE name = 'Врач-терапевт' LIMIT 1),
    3343,
    3,
    8,
    false,
    (SELECT id FROM users WHERE email = 'андрей9@company.com' LIMIT 1),
    '2025-05-27',
    '2025-07-27'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'UI/UX Designer',
    'Разработка интерфейсов, проведение юзабилити-тестов.',
    (SELECT id FROM categories WHERE name = 'UI/UX дизайн' LIMIT 1),
    2481,
    3,
    4,
    true,
    (SELECT id FROM users WHERE email = 'андрей9@company.com' LIMIT 1),
    '2025-04-10',
    '2025-06-04'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'QA Engineer',
    'Ручное и автоматизированное тестирование веб-приложений.',
    (SELECT id FROM categories WHERE name = 'QA' LIMIT 1),
    3011,
    3,
    7,
    true,
    (SELECT id FROM users WHERE email = 'андрей9@company.com' LIMIT 1),
    '2024-12-24',
    '2025-04-03'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Data Scientist',
    'Разработка ML моделей, анализ данных.',
    (SELECT id FROM categories WHERE name = 'Data Science' LIMIT 1),
    2985,
    3,
    5,
    false,
    (SELECT id FROM users WHERE email = 'андрей9@company.com' LIMIT 1),
    '2024-10-27',
    '2025-01-25'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Бухгалтер',
    'Ведение бухгалтерского учета, подготовка отчетности.',
    (SELECT id FROM categories WHERE name = 'Бухгалтер' LIMIT 1),
    4768,
    3,
    4,
    false,
    (SELECT id FROM users WHERE email = 'андрей9@company.com' LIMIT 1),
    '2025-02-16',
    '2025-03-23'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Data Scientist',
    'Разработка ML моделей, анализ данных.',
    (SELECT id FROM categories WHERE name = 'Data Science' LIMIT 1),
    507,
    1,
    4,
    true,
    (SELECT id FROM users WHERE email = 'наталья10@company.com' LIMIT 1),
    '2024-09-05',
    '2024-12-09'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'QA Engineer',
    'Ручное и автоматизированное тестирование веб-приложений.',
    (SELECT id FROM categories WHERE name = 'QA' LIMIT 1),
    2520,
    3,
    7,
    false,
    (SELECT id FROM users WHERE email = 'наталья10@company.com' LIMIT 1),
    '2025-07-04',
    '2025-07-29'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'SMM-специалист',
    'Продвижение бренда в соцсетях.',
    (SELECT id FROM categories WHERE name = 'Маркетолог' LIMIT 1),
    4347,
    1,
    4,
    true,
    (SELECT id FROM users WHERE email = 'наталья10@company.com' LIMIT 1),
    '2024-12-18',
    '2025-02-26'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'UI/UX Designer',
    'Разработка интерфейсов, проведение юзабилити-тестов.',
    (SELECT id FROM categories WHERE name = 'UI/UX дизайн' LIMIT 1),
    3248,
    3,
    5,
    false,
    (SELECT id FROM users WHERE email = 'наталья10@company.com' LIMIT 1),
    '2024-09-12',
    '2024-11-18'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Angular Developer',
    'Разработка корпоративных интерфейсов на Angular.',
    (SELECT id FROM categories WHERE name = 'Frontend разработка' LIMIT 1),
    2624,
    1,
    6,
    true,
    (SELECT id FROM users WHERE email = 'наталья10@company.com' LIMIT 1),
    '2024-07-01',
    '2024-09-25'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Главный бухгалтер',
    'Организация финансовой отчетности компании.',
    (SELECT id FROM categories WHERE name = 'Бухгалтер' LIMIT 1),
    1744,
    3,
    4,
    false,
    (SELECT id FROM users WHERE email = 'наталья10@company.com' LIMIT 1),
    '2025-01-19',
    '2025-04-03'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Учитель математики',
    'Преподавание математики в старших классах.',
    (SELECT id FROM categories WHERE name = 'Учитель математики' LIMIT 1),
    4312,
    0,
    2,
    true,
    (SELECT id FROM users WHERE email = 'наталья10@company.com' LIMIT 1),
    '2024-07-23',
    '2024-09-07'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Cloud Engineer',
    'Поддержка облачной инфраструктуры (AWS, GCP).',
    (SELECT id FROM categories WHERE name = 'DevOps' LIMIT 1),
    523,
    2,
    5,
    true,
    (SELECT id FROM users WHERE email = 'наталья10@company.com' LIMIT 1),
    '2025-02-12',
    '2025-03-13'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Терапевт',
    'Амбулаторный прием пациентов, диагностика, назначение лечения.',
    (SELECT id FROM categories WHERE name = 'Врач-терапевт' LIMIT 1),
    1582,
    3,
    7,
    true,
    (SELECT id FROM users WHERE email = 'наталья10@company.com' LIMIT 1),
    '2024-08-25',
    '2024-09-29'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Java Developer',
    'Разработка и поддержка backend-сервисов на Java/Spring.',
    (SELECT id FROM categories WHERE name = 'Backend разработка' LIMIT 1),
    3529,
    1,
    3,
    false,
    (SELECT id FROM users WHERE email = 'наталья10@company.com' LIMIT 1),
    '2024-07-29',
    '2024-10-09'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Cloud Engineer',
    'Поддержка облачной инфраструктуры (AWS, GCP).',
    (SELECT id FROM categories WHERE name = 'DevOps' LIMIT 1),
    715,
    0,
    2,
    true,
    (SELECT id FROM users WHERE email = 'игорь11@company.com' LIMIT 1),
    '2025-04-25',
    '2025-05-07'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'ML Engineer',
    'Продакшн-развертывание ML решений.',
    (SELECT id FROM categories WHERE name = 'Data Science' LIMIT 1),
    2418,
    0,
    4,
    false,
    (SELECT id FROM users WHERE email = 'игорь11@company.com' LIMIT 1),
    '2025-06-15',
    '2025-07-19'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Терапевт',
    'Амбулаторный прием пациентов, диагностика, назначение лечения.',
    (SELECT id FROM categories WHERE name = 'Врач-терапевт' LIMIT 1),
    1446,
    1,
    5,
    true,
    (SELECT id FROM users WHERE email = 'игорь11@company.com' LIMIT 1),
    '2024-08-05',
    '2024-09-04'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Главный бухгалтер',
    'Организация финансовой отчетности компании.',
    (SELECT id FROM categories WHERE name = 'Бухгалтер' LIMIT 1),
    570,
    1,
    6,
    true,
    (SELECT id FROM users WHERE email = 'игорь11@company.com' LIMIT 1),
    '2025-01-31',
    '2025-03-04'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'UI/UX Designer',
    'Разработка интерфейсов, проведение юзабилити-тестов.',
    (SELECT id FROM categories WHERE name = 'UI/UX дизайн' LIMIT 1),
    903,
    0,
    4,
    false,
    (SELECT id FROM users WHERE email = 'игорь11@company.com' LIMIT 1),
    '2025-03-15',
    '2025-04-13'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Маркетолог',
    'Разработка и реализация маркетинговых стратегий.',
    (SELECT id FROM categories WHERE name = 'Маркетолог' LIMIT 1),
    3083,
    3,
    6,
    false,
    (SELECT id FROM users WHERE email = 'зарина12@company.com' LIMIT 1),
    '2025-06-25',
    '2025-07-16'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Angular Developer',
    'Разработка корпоративных интерфейсов на Angular.',
    (SELECT id FROM categories WHERE name = 'Frontend разработка' LIMIT 1),
    4216,
    0,
    5,
    false,
    (SELECT id FROM users WHERE email = 'зарина12@company.com' LIMIT 1),
    '2024-09-16',
    '2024-12-05'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Учитель математики',
    'Преподавание математики в старших классах.',
    (SELECT id FROM categories WHERE name = 'Учитель математики' LIMIT 1),
    3286,
    3,
    5,
    true,
    (SELECT id FROM users WHERE email = 'зарина12@company.com' LIMIT 1),
    '2024-08-15',
    '2024-10-28'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'UI/UX Designer',
    'Разработка интерфейсов, проведение юзабилити-тестов.',
    (SELECT id FROM categories WHERE name = 'UI/UX дизайн' LIMIT 1),
    3868,
    1,
    2,
    false,
    (SELECT id FROM users WHERE email = 'зарина12@company.com' LIMIT 1),
    '2025-04-03',
    '2025-06-24'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Маркетолог',
    'Разработка и реализация маркетинговых стратегий.',
    (SELECT id FROM categories WHERE name = 'Маркетолог' LIMIT 1),
    3898,
    0,
    3,
    false,
    (SELECT id FROM users WHERE email = 'руслан13@company.com' LIMIT 1),
    '2024-07-25',
    '2024-10-09'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Angular Developer',
    'Разработка корпоративных интерфейсов на Angular.',
    (SELECT id FROM categories WHERE name = 'Frontend разработка' LIMIT 1),
    1323,
    2,
    3,
    true,
    (SELECT id FROM users WHERE email = 'руслан13@company.com' LIMIT 1),
    '2025-02-18',
    '2025-05-25'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'ML Engineer',
    'Продакшн-развертывание ML решений.',
    (SELECT id FROM categories WHERE name = 'Data Science' LIMIT 1),
    1398,
    0,
    2,
    true,
    (SELECT id FROM users WHERE email = 'руслан13@company.com' LIMIT 1),
    '2025-01-28',
    '2025-04-21'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Репетитор по математике',
    'Индивидуальные занятия по алгебре и геометрии.',
    (SELECT id FROM categories WHERE name = 'Учитель математики' LIMIT 1),
    3909,
    1,
    6,
    true,
    (SELECT id FROM users WHERE email = 'руслан13@company.com' LIMIT 1),
    '2024-09-10',
    '2024-11-19'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Cloud Engineer',
    'Поддержка облачной инфраструктуры (AWS, GCP).',
    (SELECT id FROM categories WHERE name = 'DevOps' LIMIT 1),
    4487,
    0,
    4,
    true,
    (SELECT id FROM users WHERE email = 'руслан13@company.com' LIMIT 1),
    '2024-08-09',
    '2024-09-25'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Семейный врач',
    'Ведение пациентов всех возрастов, профилактика заболеваний.',
    (SELECT id FROM categories WHERE name = 'Врач-терапевт' LIMIT 1),
    1031,
    0,
    5,
    false,
    (SELECT id FROM users WHERE email = 'руслан13@company.com' LIMIT 1),
    '2024-11-05',
    '2025-01-12'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'QA Engineer',
    'Ручное и автоматизированное тестирование веб-приложений.',
    (SELECT id FROM categories WHERE name = 'QA' LIMIT 1),
    4142,
    0,
    4,
    false,
    (SELECT id FROM users WHERE email = 'руслан13@company.com' LIMIT 1),
    '2024-07-25',
    '2024-10-02'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Бухгалтер',
    'Ведение бухгалтерского учета, подготовка отчетности.',
    (SELECT id FROM categories WHERE name = 'Бухгалтер' LIMIT 1),
    1658,
    2,
    5,
    true,
    (SELECT id FROM users WHERE email = 'руслан13@company.com' LIMIT 1),
    '2024-07-11',
    '2024-09-09'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Python Backend Developer',
    'Разработка REST API и интеграция с внешними сервисами.',
    (SELECT id FROM categories WHERE name = 'Backend разработка' LIMIT 1),
    568,
    0,
    3,
    true,
    (SELECT id FROM users WHERE email = 'руслан13@company.com' LIMIT 1),
    '2024-10-16',
    '2024-12-14'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'UI/UX Designer',
    'Разработка интерфейсов, проведение юзабилити-тестов.',
    (SELECT id FROM categories WHERE name = 'UI/UX дизайн' LIMIT 1),
    2030,
    0,
    4,
    false,
    (SELECT id FROM users WHERE email = 'руслан13@company.com' LIMIT 1),
    '2024-12-23',
    '2025-04-01'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Cloud Engineer',
    'Поддержка облачной инфраструктуры (AWS, GCP).',
    (SELECT id FROM categories WHERE name = 'DevOps' LIMIT 1),
    3120,
    0,
    4,
    false,
    (SELECT id FROM users WHERE email = 'валерия14@company.com' LIMIT 1),
    '2024-09-17',
    '2024-12-02'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'SMM-специалист',
    'Продвижение бренда в соцсетях.',
    (SELECT id FROM categories WHERE name = 'Маркетолог' LIMIT 1),
    3895,
    2,
    4,
    false,
    (SELECT id FROM users WHERE email = 'валерия14@company.com' LIMIT 1),
    '2024-06-28',
    '2024-09-14'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Frontend Developer',
    'Разработка SPA приложений на React/Vue.',
    (SELECT id FROM categories WHERE name = 'Frontend разработка' LIMIT 1),
    4176,
    3,
    7,
    true,
    (SELECT id FROM users WHERE email = 'валерия14@company.com' LIMIT 1),
    '2025-01-13',
    '2025-02-26'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Репетитор по математике',
    'Индивидуальные занятия по алгебре и геометрии.',
    (SELECT id FROM categories WHERE name = 'Учитель математики' LIMIT 1),
    3009,
    1,
    5,
    true,
    (SELECT id FROM users WHERE email = 'валерия14@company.com' LIMIT 1),
    '2025-01-15',
    '2025-04-19'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Python Backend Developer',
    'Разработка REST API и интеграция с внешними сервисами.',
    (SELECT id FROM categories WHERE name = 'Backend разработка' LIMIT 1),
    1211,
    3,
    7,
    false,
    (SELECT id FROM users WHERE email = 'валерия14@company.com' LIMIT 1),
    '2024-06-26',
    '2024-09-16'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'QA Engineer',
    'Ручное и автоматизированное тестирование веб-приложений.',
    (SELECT id FROM categories WHERE name = 'QA' LIMIT 1),
    1885,
    1,
    3,
    false,
    (SELECT id FROM users WHERE email = 'валерия14@company.com' LIMIT 1),
    '2025-02-11',
    '2025-04-18'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Product Designer',
    'Участие в проектировании продуктов, дизайн-макеты.',
    (SELECT id FROM categories WHERE name = 'UI/UX дизайн' LIMIT 1),
    2947,
    3,
    6,
    false,
    (SELECT id FROM users WHERE email = 'валерия14@company.com' LIMIT 1),
    '2025-04-16',
    '2025-07-16'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Data Scientist',
    'Разработка ML моделей, анализ данных.',
    (SELECT id FROM categories WHERE name = 'Data Science' LIMIT 1),
    1480,
    3,
    4,
    true,
    (SELECT id FROM users WHERE email = 'валерия14@company.com' LIMIT 1),
    '2025-05-06',
    '2025-05-22'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Семейный врач',
    'Ведение пациентов всех возрастов, профилактика заболеваний.',
    (SELECT id FROM categories WHERE name = 'Врач-терапевт' LIMIT 1),
    4702,
    3,
    4,
    true,
    (SELECT id FROM users WHERE email = 'оскар15@company.com' LIMIT 1),
    '2025-01-04',
    '2025-03-07'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Frontend Developer',
    'Разработка SPA приложений на React/Vue.',
    (SELECT id FROM categories WHERE name = 'Frontend разработка' LIMIT 1),
    3487,
    2,
    3,
    false,
    (SELECT id FROM users WHERE email = 'оскар15@company.com' LIMIT 1),
    '2025-02-25',
    '2025-04-10'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Учитель математики',
    'Преподавание математики в старших классах.',
    (SELECT id FROM categories WHERE name = 'Учитель математики' LIMIT 1),
    703,
    1,
    2,
    true,
    (SELECT id FROM users WHERE email = 'оскар15@company.com' LIMIT 1),
    '2024-11-03',
    '2025-01-25'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'UI/UX Designer',
    'Разработка интерфейсов, проведение юзабилити-тестов.',
    (SELECT id FROM categories WHERE name = 'UI/UX дизайн' LIMIT 1),
    2830,
    0,
    5,
    true,
    (SELECT id FROM users WHERE email = 'оскар15@company.com' LIMIT 1),
    '2025-04-20',
    '2025-05-14'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Data Scientist',
    'Разработка ML моделей, анализ данных.',
    (SELECT id FROM categories WHERE name = 'Data Science' LIMIT 1),
    1676,
    3,
    4,
    false,
    (SELECT id FROM users WHERE email = 'оскар15@company.com' LIMIT 1),
    '2024-08-11',
    '2024-10-02'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'QA Engineer',
    'Ручное и автоматизированное тестирование веб-приложений.',
    (SELECT id FROM categories WHERE name = 'QA' LIMIT 1),
    4878,
    0,
    4,
    false,
    (SELECT id FROM users WHERE email = 'оскар15@company.com' LIMIT 1),
    '2024-12-23',
    '2025-03-04'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Cloud Engineer',
    'Поддержка облачной инфраструктуры (AWS, GCP).',
    (SELECT id FROM categories WHERE name = 'DevOps' LIMIT 1),
    4354,
    0,
    3,
    true,
    (SELECT id FROM users WHERE email = 'оскар15@company.com' LIMIT 1),
    '2024-12-14',
    '2025-01-11'
);
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES (
    'Главный бухгалтер',
    'Организация финансовой отчетности компании.',
    (SELECT id FROM categories WHERE name = 'Бухгалтер' LIMIT 1),
    1279,
    2,
    7,
    true,
    (SELECT id FROM users WHERE email = 'оскар15@company.com' LIMIT 1),
    '2025-05-23',
    '2025-07-31'
);
